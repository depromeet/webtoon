package com.depromeet.webtoon.core.domain.rating.repository

import com.depromeet.webtoon.core.domain.rating.model.WebtoonRatingAverage
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface WebtoonRatingAverageRepository : JpaRepository<WebtoonRatingAverage, Long> {
    @Query("select wra from WebtoonRatingAverage wra where wra.webtoon.id = :webtoonId")
    fun findByWebtoonId(webtoonId: Long): WebtoonRatingAverage?

    fun findByWebtoon(webtoon: Webtoon): WebtoonRatingAverage?
}
