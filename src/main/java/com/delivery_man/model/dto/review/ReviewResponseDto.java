package com.delivery_man.model.dto.review;

import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private final Long id;
    private final String author;
    private final String content;
    private final int rating;

    public ReviewResponseDto(Long id, String author, String content, int rating) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.rating = rating;
    }
}
