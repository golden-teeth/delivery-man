package com.delivery_man.model.dto.menu;

import com.delivery_man.constant.MenuStatus;
import com.delivery_man.model.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MenuWithPictureResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private MenuStatus status;
    private LocalDateTime updateAt;
    private Long shopId;
    private String publicUrl;

    public MenuWithPictureResponseDto(Menu menu, String publicUrl) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.status = menu.getStatus();
        this.updateAt = menu.getUpdatedAt();
        this.shopId = menu.getShop().getId();
        this.publicUrl = publicUrl;
    }
}
