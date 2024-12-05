package com.delivery_man.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.mapping.ToOne;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="review")
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Review(String content, int rating, User user, Order order) {
        this.content = content;
        this.rating = rating;
        this.user = user;
        this.order = order;
    }

    public Review() {

    }

    public void updateOrder(Order order){
        this.order = order;
    }
}
