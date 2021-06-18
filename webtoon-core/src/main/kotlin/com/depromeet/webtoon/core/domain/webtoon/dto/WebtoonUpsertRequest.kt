package com.depromeet.webtoon.core.domain.webtoon.dto

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon

data class WebtoonUpsertRequest(
    val title: String,
    val site: WebtoonSite,
    val authors: List<Author>,
    val dayOfWeeks: List<WeekDay>,
    val popularity: Int,
    val thumbnail: String,
    val summary: String,
    val genres: List<String>,
    val url: String,
    val score: Double,
    val backgroundColor: String,
    val isComplete: Boolean,
)

fun WebtoonUpsertRequest.convertToWebtoon() = Webtoon(
        title = title,
        site = site,
        authors = authors,
        weekdays = dayOfWeeks,
        popularity = popularity,
        thumbnail = thumbnail,
        summary = summary,
        genres = genres,
        url = url,
        score = score,
        backgroundColor = backgroundColor,
        isComplete = isComplete
    )
