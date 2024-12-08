package com.delivery_man.repository;

import com.delivery_man.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByOrderIdInAndUserIdNot(List<Long> ordersIds, Long sessionId);

    Page<Review> findByOrderIdInAndUserIdNotAndRatingBetween(List<Long> orderIds, Long userId, int ratingMin, int ratingMax, Pageable pageable);
}
