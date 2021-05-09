package com.depromeet.webtoon.core.domain.account

import com.depromeet.webtoon.core.domain.account.model.Account
import java.time.LocalDateTime


fun accountFixture(
    id: Long = 1L,
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
