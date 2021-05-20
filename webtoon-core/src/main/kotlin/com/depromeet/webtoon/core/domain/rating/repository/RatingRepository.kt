package com.depromeet.webtoon.core.domain.rating.repository

import com.depromeet.webtoon.core.domain.rating.dto.ScoreDto
import com.depromeet.webtoon.core.domain.rating.model.Rating
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RatingRepository : JpaRepository<Rating, Long> {

    fun findByWebtoon(webtoon: Webtoon): Rating?

    @Query(
        "select r from Rating r where r.webtoon.id = :webtoonId and r.account.id = :accountId"
    )
    fun findByWebtoonIdAndAccountId(webtoonId: Long, accountId: Long): Rating?

    @Query(
        "select avg(r.storyScore) from Rating r where r.webtoon = :webtoon"
    )
    fun getAvgStoryScore(webtoon: Webtoon): Double

    @Query(
        "select avg(r.drawingScore) from Rating r where r.webtoon = :webtoon"
    )
    fun getAvgDrawingScore(webtoon: Webtoon): Double


}
