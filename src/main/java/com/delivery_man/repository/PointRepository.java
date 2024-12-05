package com.delivery_man.repository;

import com.delivery_man.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    @Transactional
    void deletePointByCreatedAtLessThan(LocalDateTime cutoffDateTime);

    List<Point> findByUserId(Long userId);
}
