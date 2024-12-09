package com.delivery_man.repository;

import com.delivery_man.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserIdAndMenuId(Long userId, Long MenuId);

    List<Cart> findByUserId(Long userId);
}
