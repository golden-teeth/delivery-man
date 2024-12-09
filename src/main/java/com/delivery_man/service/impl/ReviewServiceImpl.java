package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.constant.errorcode.OrderErrorCode;
import com.delivery_man.constant.errorcode.ReviewErrorCode;
import com.delivery_man.constant.errorcode.UserErrorCode;
import com.delivery_man.model.dto.review.ReviewCreateRequestDto;
import com.delivery_man.model.dto.review.ReviewResponseDto;
import com.delivery_man.model.entity.Order;
import com.delivery_man.model.entity.Review;
import com.delivery_man.model.entity.User;
import com.delivery_man.repository.OrderRepository;
import com.delivery_man.repository.ReviewRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 생성
     * @param dto
     * @param orderId
     * @param sessionId
     * @return
     */
    @Override
    public ReviewResponseDto createReview(ReviewCreateRequestDto dto,Long orderId, Long sessionId) {
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(OrderErrorCode.ORDER_NOT_FOUND));

        if (!sessionId.equals(findOrder.getUser().getId())) {
            throw new ApiException(ReviewErrorCode.INVALID_AUTHOR);
        }

        User findUser = userRepository.findById(sessionId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        if (!"done".equals(findOrder.getStatus())) {
            throw new ApiException(ReviewErrorCode.REVIEW_NOT_CREATED);
        }

        Review review = new Review(dto.getContent(), dto.getRating(), findUser, findOrder);
        reviewRepository.save(review);

        return new ReviewResponseDto(review.getId(), review.getUser().getName(), review.getContent(), review.getRating());
    }
}
