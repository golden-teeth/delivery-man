package com.delivery_man.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserValidDto {
    private Long userId;
    private Long sessionId;
}
