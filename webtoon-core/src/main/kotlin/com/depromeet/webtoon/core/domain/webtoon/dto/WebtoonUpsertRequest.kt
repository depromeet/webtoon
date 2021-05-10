package com.depromeet.webtoon.core.domain.webtoon.dto

import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay

data class WebtoonUpsertRequest(
    val title: String,
    val site: WebtoonSite,
    val authors: List<Author>,
    val dayOfWeeks: List<WeekDay>,
    val popularity: Int,
    val thumbnail: String,
    val summary: String,
    val url: String
)
