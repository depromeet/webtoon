package com.depromeet.webtoon.core.domain.rating

import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.rating.model.Rating
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import java.time.LocalDateTime

fun ratingFixture(
    id: Long? = null,
    webtoon: Webtoon = webtoonFixture(1L),
    account: Account = accountFixture(1L),
    storyScore: Double = 5.0,
    drawingScore: Double = 5.0,
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime = LocalDateTime.now()


) = Rating (
    id = id,
    webtoon = webtoon,
    account = account,
    storyScore = storyScore,
    drawingScore = drawingScore,
    createdAt = createdAt,
    modifiedAt = modifiedAt
)
