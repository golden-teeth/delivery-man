package com.delivery_man.repository;

import com.delivery_man.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
