package com.delivery_man.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserValidDto {
    private Long userId;
    private Long sessionId;
}
