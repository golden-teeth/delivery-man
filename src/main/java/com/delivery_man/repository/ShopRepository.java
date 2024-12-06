package com.delivery_man.repository;

import com.delivery_man.constant.ShopStatus;
import com.delivery_man.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAllByUserId(Long sessionId);

    List<Shop> findAllByName(String shopName);

    @Query("SELECT s " +
            "FROM Shop s " +
            "LEFT JOIN Advertisement a ON s.id = a.shop.id " +
            "WHERE s.name = :name AND s.status = :status " +
            "ORDER BY " +
            "CASE WHEN a.status = 'APPLY' THEN 1" +
            "     WHEN a.status = 'REQUEST' THEN 2" +
            "     WHEN a.status = 'REJECT' THEN 3" +
            "     ELSE 4 END," +
            "a.bid DESC," +
            "a.appliedAt ASC," +
            "s.createdAt DESC")
    List<Shop> findAllByNameAndStatus(@Param("name") String shopName, @Param("status") ShopStatus status);

    @Query("select s from Shop s where s.id = :shopId and s.status = :status")
    Optional<Shop> findShopByIdAndStatus(@Param("shopId") Long shopId, @Param("status") ShopStatus status);

}
