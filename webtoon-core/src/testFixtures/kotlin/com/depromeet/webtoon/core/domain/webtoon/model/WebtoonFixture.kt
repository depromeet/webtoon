package com.depromeet.webtoon.core.domain.webtoon.model

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.model.Author
import java.time.LocalDateTime

fun webtoonFixture(
    id: Long? = null,
    site: WebtoonSite = WebtoonSite.NAVER,
    title: String = "테스트작품",
    authors: List<Author> = listOf(authorFixture(1L, "테스트 작가")),
    score: Double = 9.5,
    isComplete: Boolean = false,
    backgroundColor: String = "AB32FE",
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime = LocalDateTime.now(),
) = Webtoon(
    id = id,
    site = site,
    title = title,
    authors = authors,
    score = score,
    isComplete = isComplete,
    backgroundColor = backgroundColor,
    createdAt = createdAt,
    modifiedAt = modifiedAt
)
