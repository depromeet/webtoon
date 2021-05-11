package com.depromeet.webtoon.core.domain.review.repository

import com.depromeet.webtoon.core.domain.review.model.Review
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {

    @Query(
        "select avg(r.storyScore) from Review r where r.webtoon = :webtoon"
    )
    fun getAvgStoryScore(webtoon: Webtoon): Double

    @Query(
        "select avg(r.drawingScore) from Review r where r.webtoon = :webtoon"
    )
    fun getAvgDrawingScore(webtoon: Webtoon): Double

    // select comment from review where webtoon_id = 1;

    @Query(
        "select r.comment from Review r where r.webtoon = :webtoon"
    )
    fun getComments(webtoon: Webtoon): MutableList<String>
}
