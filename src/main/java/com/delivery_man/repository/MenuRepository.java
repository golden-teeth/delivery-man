package com.delivery_man.repository;

import com.delivery_man.model.entity.Menu;
import com.delivery_man.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    List<Menu> findByShop(Shop findShop);
}
