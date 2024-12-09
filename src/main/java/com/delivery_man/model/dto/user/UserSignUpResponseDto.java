package com.delivery_man.model.dto.user;

import com.delivery_man.model.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSignUpResponseDto {

    private final Long id;

    private final String email;

    private final String role;

    private final String name;

    private final LocalDateTime updatedAt;

    private final String publicUrl;

    public UserSignUpResponseDto(User user, String publicUrl) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.name = user.getName();
        this.updatedAt = user.getUpdatedAt();
        this.publicUrl = publicUrl;
    }

    public UserSignUpResponseDto(Long id, String email, String role, String name, LocalDateTime updatedAt, String publicUrl) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.name = name;
        this.updatedAt = updatedAt;
        this.publicUrl = publicUrl;
    }
}
