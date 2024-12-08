package com.delivery_man.model.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {


    private Long userId;

    private Long sessionUserId;

    public void updateIds(Long userId,Long sessionUserId) {
        this.userId = userId;
        this.sessionUserId = sessionUserId;
    }
}
