package com.depromeet.webtoon.core.domain.webtoon.dto

import com.depromeet.webtoon.common.type.BackgroundColor
import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.core.domain.author.model.Author

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
    val backgroundColor: BackgroundColor,
    val isComplete: Boolean,
)
