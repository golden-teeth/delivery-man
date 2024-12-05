package com.delivery_man.constant;

import com.delivery_man.entity.Review;
import com.delivery_man.repository.ReviewRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public enum ReviewFilterType {
    CREATE {
        @Override
        public Pageable createPageable(int page, int size) {
            return PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        }

        @Override
        public List<Review> filterReviews(ReviewRepository reviewRepository, List<Long> orderIds, Long sessionId, int ratingMin, int ratingMax, Pageable pageable) {
            return reviewRepository.findByOrderIdInAndUserIdNotAndRatingBetween(
                    orderIds, sessionId, ratingMin, ratingMax, pageable
            ).getContent();
        }
    };

    public abstract Pageable createPageable(int page, int size);

    public abstract List<Review> filterReviews(ReviewRepository reviewRepository, List<Long> orderIds, Long sessionId, int ratingMin, int ratingMax, Pageable pageable);
}
