package com.delivery_man.repository;

import com.delivery_man.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByOrderIdInAndUserIdNot(List<Long> ordersIds, Long sessionId);
}
