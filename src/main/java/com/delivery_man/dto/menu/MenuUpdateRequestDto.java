package com.delivery_man.dto.menu;

import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class MenuUpdateRequestDto {
    @Null
    private Long userId;
    @Null
    private Long shopId;

    @Null
    private Long menuId;

    private String name;

    private BigDecimal price;

    public void setIds(Long userId,Long shopId,Long menuId){
        this.userId=userId;
        this.shopId=shopId;
        this.menuId=menuId;
    }
}
