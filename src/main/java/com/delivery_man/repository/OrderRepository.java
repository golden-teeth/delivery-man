package com.delivery_man.repository;

import com.delivery_man.dto.DashBoardGetResponseDto;
import com.delivery_man.entity.Order;
import com.delivery_man.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByShop(Shop findShop);

    List<Order> findByShopId(Long id);

    @Query("select new com.delivery_man.dto.DashBoardGetResponseDto(" +
            "COUNT(o.id), SUM(o.totalPrice), " +
            "CASE WHEN :shopName IS NULL THEN 'ALL' ELSE s.name END) " +
            "from Order o " +
            "JOIN o.shop s " +
            "WHERE (:shopName IS NULL OR s.name = :shopName) " +
            "AND o.status = 'done' " +
            "AND o.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY CASE WHEN :shopName IS NULL THEN 'ALL' ELSE s.name END")
    DashBoardGetResponseDto findOrderCountAndAmount(@Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endTime,
                                                    @Param("shopName") String shopName);
}
