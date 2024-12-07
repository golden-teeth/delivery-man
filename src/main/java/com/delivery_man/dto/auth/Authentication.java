package com.delivery_man.dto.auth;

import lombok.Getter;

@Getter
public class Authentication {

    private final Long id;

    private final String email;

    private final String grade;

    public Authentication(Long id, String email, String grade) {
        this.id = id;
        this.email = email;
        this.grade = grade;
    }
}
