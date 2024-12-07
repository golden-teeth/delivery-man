package com.delivery_man.service;

import com.delivery_man.dto.review.ReviewCreateRequestDto;
import com.delivery_man.dto.review.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto createReview(ReviewCreateRequestDto dto, Long orderId, Long sessionId);
}
