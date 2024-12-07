package com.delivery_man.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    @NotNull
    private Long menuId;

    private Long userId;

    private Long sessionUserId;

    public void updateIds(Long userId,Long sessionUserId) {
        this.userId = userId;
        this.sessionUserId = sessionUserId;
    }
}
