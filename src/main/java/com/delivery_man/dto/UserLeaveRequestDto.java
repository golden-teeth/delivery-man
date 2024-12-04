package com.delivery_man.dto;

import lombok.Getter;

@Getter
public class UserLeaveRequestDto {

    private final String password;

    public UserLeaveRequestDto(String password) {
        this.password = password;
    }
}
