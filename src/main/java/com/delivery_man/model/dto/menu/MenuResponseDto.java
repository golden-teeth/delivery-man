package com.delivery_man.model.dto.menu;

import com.delivery_man.model.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MenuResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String status;
    private LocalDateTime updateAt;
    private Long shopId;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.status = menu.getStatus();
        this.updateAt = menu.getUpdatedAt();
        this.shopId = menu.getShop().getId();
    }
}
