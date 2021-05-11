package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay

data class WebtoonDetailResponse(
    var id: Long,
    var title: String,
    var thumbnail: String,
    var url: String,
    var authors: MutableList<Author>,
    var site: WebtoonSite,
    var weekday: MutableList<WeekDay>,
    var summary: String,
    val storyScore: Double,
    val drawingScore: Double,
    val comments: MutableList<String>
)
