package com.depromeet.webtoon.core.application.imports

import com.depromeet.webtoon.core.application.imports.dto.WebtoonImportRequest
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WebtoonSite.NAVER
import com.depromeet.webtoon.core.type.WeekDay

fun webtoonImportRequestFixture(
    title: String = "고양이 일상",
    url: String = "naver.com",
    thumbnailImage: String = "",
    dayOfWeeks: List<WeekDay> = listOf(WeekDay.MON, WeekDay.THU),
    authors: List<String> = listOf("감자", "웅"),
    site: WebtoonSite = NAVER,
    genres: List<String> = listOf("고양이", "일상"),
    score: Double = 4.8,
    popular: Int = 2,
    summary: String = "귀여운 고양이 이야기",
) = WebtoonImportRequest(
    title = title,
    url = url,
    thumbnailImage = thumbnailImage,
    weekdays = dayOfWeeks,
    authors = authors,
    site = site,
    genres = genres,
    score = score,
    popular = popular,
    summary = summary
)
