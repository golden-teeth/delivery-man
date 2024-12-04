package com.delivery_man.repository;

import com.delivery_man.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAllByUserId(Long sessionId);
}
