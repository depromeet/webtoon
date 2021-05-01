package com.depromeet.webtoon.core.application.imports.dto

import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay
import java.time.DayOfWeek

data class WebtoonImportRequest(
    val title: String,
    val url: String,
    val thumbnailImage: String,
    val dayOfWeeks: List<WeekDay>,
    val authors: List<String>,
    val site: WebtoonSite,
    val genres: List<String>,
    val score: Double,
    val popular: Int,
)
