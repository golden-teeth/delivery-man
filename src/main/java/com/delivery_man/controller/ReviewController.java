package com.delivery_man.controller;

import com.delivery_man.config.Const;
import com.delivery_man.dto.Authentication;
import com.delivery_man.dto.ReviewCreateRequestDto;
import com.delivery_man.dto.ReviewResponseDto;
import com.delivery_man.service.OrderService;
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
