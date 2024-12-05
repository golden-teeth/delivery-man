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

    @Query("select s from Shop s where s.id = :shopId and s.status = :status")
    Optional<Shop> findShopByIdAndStatus(@Param("shopId") Long shopId, @Param("status") ShopStatus status);

    @Query("select s from Shop s where s.name = :name and s.status = :status")
    List<Shop> findAllByNameAndStatus(@Param("name") String shopName, @Param("status") ShopStatus status);
}
