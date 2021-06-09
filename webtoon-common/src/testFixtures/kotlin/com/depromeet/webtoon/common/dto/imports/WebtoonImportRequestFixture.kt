package com.depromeet.webtoon.common.dto.imports

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay

fun webtoonImportRequestFixture(
    title: String = "고양이 일상",
    url: String = "naver.com",
    thumbnailImage: String = "",
    dayOfWeeks: List<WeekDay> = listOf(WeekDay.MON, WeekDay.THU),
    authors: List<String> = listOf("감자", "웅"),
    site: WebtoonSite = WebtoonSite.NAVER,
    genres: List<String> = listOf("고양이", "일상"),
    score: Double = 4.8,
    popular: Int = 2,
    summary: String = "몬난이 고양이 이야기",
    backgroundColor: String = "AAA111",
    isComplete: Boolean = false,
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
    summary = summary,
    backgroundColor = backgroundColor,
    isComplete = isComplete,
)
