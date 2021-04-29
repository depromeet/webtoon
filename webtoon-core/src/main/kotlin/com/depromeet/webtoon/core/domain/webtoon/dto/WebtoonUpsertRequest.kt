package com.depromeet.webtoon.core.domain.webtoon.dto

import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.type.WebtoonSite
import java.time.DayOfWeek

data class WebtoonUpsertRequest(
    val title: String,
    val site: WebtoonSite,
    val authors: List<Author>,
    val dayOfWeeks: List<DayOfWeek>,
    val popularity: Int
)
