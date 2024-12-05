package com.delivery_man.service;

import com.delivery_man.dto.ReviewCreateRequestDto;
import com.delivery_man.dto.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto createReview(ReviewCreateRequestDto dto, Long orderId, Long sessionId);
}
