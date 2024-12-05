package com.delivery_man.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name="review")
@EntityListeners(AuditingEntityListener.class)
public class Review extends CreateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Review(String content, int rating, User user, Order order) {
        this.content = content;
        this.rating = rating;
        this.user = user;
        this.order = order;
    }

    public Review() {

    }

    public void updateOrder(Order order){
        this.order = order;
    }
}
