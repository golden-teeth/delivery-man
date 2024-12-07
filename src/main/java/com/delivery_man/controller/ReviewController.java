package com.delivery_man.controller;

import com.delivery_man.constant.Const;
import com.delivery_man.dto.auth.Authentication;
import com.delivery_man.dto.review.ReviewCreateRequestDto;
import com.delivery_man.dto.review.ReviewResponseDto;
import com.delivery_man.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    /**
     * 리뷰 생성 API
     * @param orderId
     * @return
     */
    @PostMapping({"/orders/{orderId}/reviews"})
    public ResponseEntity<ReviewResponseDto> createReview(
            @Valid @PathVariable Long orderId,
            @RequestBody ReviewCreateRequestDto dto,
            @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication
    ){
        Long sessionId = authentication.getId();
        ReviewResponseDto reviewResponseDto = reviewService.createReview(dto, orderId, sessionId);
        return new ResponseEntity<>(reviewResponseDto, HttpStatus.CREATED);
    }
}
