package com.depromeet.webtoon.common.dto.imports

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay

data class WebtoonImportRequest(
    val title: String,
    val url: String,
    val thumbnailImage: String,
    val weekdays: List<WeekDay>,
    val authors: List<String>,
    val site: WebtoonSite,
    val genres: List<String>,
    val score: Double,
    val popular: Int,
    val summary: String,
    val backgroundColor: String,
    val isComplete: Boolean,
)
