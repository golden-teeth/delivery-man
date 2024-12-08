package com.delivery_man.model.dto.user;

import com.delivery_man.model.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSignUpResponseDto {

    private final Long id;

    private final String email;

    private final String grade;

    private final String name;

    private final LocalDateTime updatedAt;

    private final String publicUrl;

    public UserSignUpResponseDto(User user, String publicUrl) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.grade = user.getGrade();
        this.name = user.getName();
        this.updatedAt = user.getUpdatedAt();
        this.publicUrl = publicUrl;
    }

    public UserSignUpResponseDto(Long id, String email, String grade, String name, LocalDateTime updatedAt, String publicUrl) {
        this.id = id;
        this.email = email;
        this.grade = grade;
        this.name = name;
        this.updatedAt = updatedAt;
        this.publicUrl = publicUrl;
    }
}
