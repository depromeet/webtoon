package com.depromeet.webtoon.core.domain.account

import com.depromeet.webtoon.core.domain.account.model.Account
import java.time.LocalDateTime

fun accountFixture(
    id: Long? = null,
    authToken: String = "testToken",
    nickname: String = "testNick",
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime = LocalDateTime.now()
) = Account(
    id = id,
    authToken = authToken,
    nickname = nickname,
    createdAt = createdAt,
    modifiedAt = modifiedAt
)
