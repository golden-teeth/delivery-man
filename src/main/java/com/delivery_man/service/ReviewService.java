package com.delivery_man.service;

import com.delivery_man.model.dto.review.ReviewCreateRequestDto;
import com.delivery_man.model.dto.review.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto createReview(ReviewCreateRequestDto dto, Long orderId, Long sessionId);
}
