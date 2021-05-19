package com.depromeet.webtoon.core.domain.rating

import com.depromeet.webtoon.core.domain.rating.model.WebtoonRatingAverage
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import java.time.LocalDateTime

fun webtoonRatingAverageFixture(
    id: Long? = null,
    webtoon: Webtoon = webtoonFixture(),
    totalStoryScore: Double? = null,
    totalDrawingScore: Double? = null,
    votes: Long? = null,
    storyAverage: Double? = null,
    drawingAverage: Double? = null,
    totalAverage: Double? = null,
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime = LocalDateTime.now()

) = WebtoonRatingAverage(
    id = id,
    webtoon = webtoon,
    totalStoryScore = totalStoryScore,
    totalDrawingScore = totalDrawingScore,
    votes = votes,
    storyAverage = storyAverage,
    drawingAverage = drawingAverage,
    totalAverage = totalAverage,
    createdAt = createdAt,
    modifiedAt = modifiedAt
)
