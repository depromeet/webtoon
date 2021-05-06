package com.depromeet.webtoon.core.domain.review.service

import com.depromeet.webtoon.core.domain.review.model.Review
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository

class ReviewService (
    val reviewRepository: ReviewRepository
        ) {

    fun createReview(){
        reviewRepository.save(Review())
    }
}
