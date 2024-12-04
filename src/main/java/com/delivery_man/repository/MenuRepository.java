package com.delivery_man.repository;

import com.delivery_man.entity.Menu;
import com.delivery_man.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    List<Menu> findByShop(Shop findShop);
}
