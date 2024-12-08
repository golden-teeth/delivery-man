package com.delivery_man.model.dto.menu;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class MenuCreateRequestDto {
    @Null
    private Long userId;
    @Null
    private Long shopId;

    @NotNull
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    public void setIds(Long userId,Long shopId){
        this.userId=userId;
        this.shopId=shopId;
    }
}
