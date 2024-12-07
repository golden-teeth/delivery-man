package com.delivery_man.model.entity;

import com.delivery_man.model.dto.order.OrderCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends CreateAndUpdateDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> review;


    //todo enum 타입 변경
    public Order(OrderCreateRequestDto dto) {
        this.status = "use";
    }

    public Order(List<Cart> carts) {
        this.status = "use";
        this.shop = carts.get(0).getMenu().getShop();
        this.orderItems = createOrderItemList(carts);
        this.totalPrice = calculateTotalPrice(carts);
        this.user = carts.get(0).getUser();
    }

    private BigDecimal calculateTotalPrice(List<Cart> carts) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Cart cart : carts) {
            BigDecimal menuPrice = cart.getMenu().getPrice();
            int quantity = cart.getQuantity();
            BigDecimal price = menuPrice.multiply(new BigDecimal(quantity));
            totalPrice = totalPrice.add(price);

        }
        // 표현식 변경
        totalPrice =  totalPrice.setScale(2, RoundingMode.HALF_UP);
        return totalPrice;
    }

    private List<OrderItem> createOrderItemList(List<Cart> carts) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Cart cart : carts) {
            orderItemList.add(new OrderItem(cart));
        }
        return orderItemList;
    }


    public void updateStatus( String status) {
        this.status = status;
    }


}
