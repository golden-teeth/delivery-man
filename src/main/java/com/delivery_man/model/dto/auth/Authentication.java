package com.delivery_man.model.dto.auth;

import lombok.Getter;

@Getter
public class Authentication {

    private final Long id;

    private final String email;

    private final String role;

    public Authentication(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
