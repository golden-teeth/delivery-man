package com.delivery_man.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {
    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    private final String content;

    @NotNull(message = "별점을 입력해주세요.")
    private final int rating;

    public ReviewCreateRequestDto(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }
}
