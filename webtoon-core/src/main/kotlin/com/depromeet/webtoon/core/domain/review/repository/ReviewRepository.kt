package com.depromeet.webtoon.core.domain.review.repository

import com.depromeet.webtoon.core.domain.review.model.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {
}
