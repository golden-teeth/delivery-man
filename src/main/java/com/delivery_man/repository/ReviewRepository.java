package com.delivery_man.repository;

import com.delivery_man.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByOrderIdInAndUserIdNot(List<Long> ordersIds, Long sessionId);

    @Query("select r from Review r " +
            "where r.order.id IN :ordersIds " +
            "AND r.user.id != :userId " +
            "AND r.rating BETWEEN :ratingMin AND :ratingMax")
    List<Review> findReviews(@Param("ordersIds") List<Long> ordersIds, @Param("userId") Long userId, @Param("ratingMin") int ratingMin, @Param("ratingMax") int ratingMax);
}
