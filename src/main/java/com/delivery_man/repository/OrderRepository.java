package com.delivery_man.repository;

import com.delivery_man.model.entity.Order;
import com.delivery_man.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByShop(Shop findShop);

    List<Order> findByShopId(Long id);
}
