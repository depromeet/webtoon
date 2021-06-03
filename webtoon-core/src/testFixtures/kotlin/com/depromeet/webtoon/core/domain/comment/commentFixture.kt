package com.depromeet.webtoon.core.domain.comment

import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.comment.model.Comment
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import java.time.LocalDateTime

fun commentFixture(
    id: Long? = null,
    content: String? = "testComment",
    account: Account = accountFixture(1L),
    webtoon: Webtoon = webtoonFixture(1L),
    nickname: String = account.nickname,
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime = LocalDateTime.now()
) = Comment(
    id = id,
    content = content,
    account = account,
    webtoon = webtoon,
    nickname = nickname,
    createdAt = createdAt,
    modifiedAt = modifiedAt
)
