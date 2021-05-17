package com.depromeet.webtoon.core.domain.review.repository

import com.depromeet.webtoon.core.domain.review.dto.CommentDto
import com.depromeet.webtoon.core.domain.review.dto.ScoreDto
import com.depromeet.webtoon.core.domain.review.model.Review
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {

    fun findByWebtoon(webtoon: Webtoon): Review?

    @Query(
        "select avg(r.storyScore) from Review r where r.webtoon = :webtoon"
    )
    fun getAvgStoryScore(webtoon: Webtoon): Double

    @Query(
        "select avg(r.drawingScore) from Review r where r.webtoon = :webtoon"
    )
    fun getAvgDrawingScore(webtoon: Webtoon): Double

    @Query(
        "select new com.depromeet.webtoon.core.domain.review.dto.CommentDto(r.comment, r.account.nickname) " +
            "from Review r where r.webtoon = :webtoon"
    )
    fun getComments(webtoon: Webtoon): List<CommentDto>

    @Query(
        "select new com.depromeet.webtoon.core.domain.review.dto.ScoreDto(avg(r.storyScore), avg(r.drawingScore)) " +
            "from Review r where r.webtoon = :webtoon"
    )
    fun getScores(webtoon: Webtoon): ScoreDto
}
