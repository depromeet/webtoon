package com.depromeet.webtoon.core.domain.rating

import com.depromeet.webtoon.core.domain.rating.model.WebtoonRatingAverage
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import java.time.LocalDateTime

fun webtoonRatingAverageFixture(
    id: Long? = null,
    webtoon: Webtoon = webtoonFixture(),
    totalStoryScore: Double? = 3.0,
    totalDrawingScore: Double? = 5.0,
    votes: Long? = 1,
    storyAverage: Double? = 3.0,
    drawingAverage: Double? = 5.0,
    totalAverage: Double? = 4.0,
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
