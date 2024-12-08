package com.delivery_man.model.dto.user;

import com.delivery_man.model.entity.User;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserResponseDto {

    private final Long userId;

    private final String email;

    private final String grade;

    private final String name;

    private final String status;

    private final BigDecimal totalPoints;

    public UserResponseDto(Long userId, String email, String grade, String name, String status, BigDecimal totalPoints) {
        this.userId = userId;
        this.email = email;
        this.grade = grade;
        this.name = name;
        this.status = status;
        this.totalPoints = totalPoints;
    }

    public UserResponseDto(User user, BigDecimal totalPoints) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.grade = user.getGrade();
        this.name = user.getName();
        this.status = user.getStatus();
        this.totalPoints = totalPoints;
    }
}
