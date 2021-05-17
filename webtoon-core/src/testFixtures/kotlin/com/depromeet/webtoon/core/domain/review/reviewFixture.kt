package com.depromeet.webtoon.core.domain.review

import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.review.model.Review
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import java.time.LocalDateTime

/*
fun accountFixture(
    id: Long? = null,
    deviceId: String = "testDevice",
    nickname: String = "testNick",
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime = LocalDateTime.now()
) = Account(
    id = id,
    deviceId = deviceId,
    nickname = nickname,
    createdAt = createdAt,
    modifiedAt = modifiedAt
)
*/

fun reviewFixture(
    id: Long? = null,
    webtoon: Webtoon = webtoonFixture(),
    account: Account = accountFixture(),
    comment: String = "fun",
    storyScore: Double = 3.0,
    drawingScore: Double = 5.0,
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime = LocalDateTime.now()
) = Review(
    id = id,
    webtoon = webtoon,
    account = account,
    comment = comment,
    storyScore = storyScore,
    drawingScore = drawingScore,
    createdAt = createdAt,
    modifiedAt = modifiedAt
)
