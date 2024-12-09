package com.delivery_man.model.dto.user;

import lombok.Getter;

@Getter
public class UserLeaveRequestDto {

    private final String password;

    public UserLeaveRequestDto(String password) {
        this.password = password;
    }
}
