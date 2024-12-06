package com.delivery_man.entity;

import com.delivery_man.dto.AdvertisementCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "advertisement")
@EntityListeners(AuditingEntityListener.class)
public class Advertisement extends CreateAndUpdateDateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal bid;

    @Column(nullable = false)
    private String status;

    private String rejectReason;

    private Long adminId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime appliedAt;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public void applyAd(String status, String rejectReason, Long adminId) {
        this.status = status;
        this.rejectReason = rejectReason;
        this.adminId = adminId;
    }

    public Advertisement(BigDecimal bid, Shop shop) {
        this.bid = bid;
        this.status = "REQUEST";
        this.shop = shop;
    }

    public Advertisement() {

    }
}
