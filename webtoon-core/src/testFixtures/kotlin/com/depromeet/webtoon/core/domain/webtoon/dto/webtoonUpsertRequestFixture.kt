package com.depromeet.webtoon.core.domain.webtoon.dto

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.model.Author

fun webtoonUpsertRequestFixture(
    title: String = "업데이트웹툰",
    site: WebtoonSite = WebtoonSite.DAUM,
    authors: List<Author> = listOf(authorFixture()),
    dayOfWeeks: List<WeekDay> = listOf(WeekDay.MON),
    popularity: Int = 1,
    thumbnail: String = "섬네일url",
    summary: String = "줄거리",
    genres: List<String> = listOf("드라마"),
    url: String = "웹툰url",
    score: Double = 10.0,
    backgroundColor: String = "ABC123",
    isComplete: Boolean = true
) = WebtoonUpsertRequest(
    title = title,
    site = site,
    authors = authors,
    dayOfWeeks = dayOfWeeks,
    popularity = popularity,
    thumbnail = thumbnail,
    summary = summary,
    genres = genres,
    url = url,
    score = score,
    backgroundColor = backgroundColor,
    isComplete =  isComplete
)
