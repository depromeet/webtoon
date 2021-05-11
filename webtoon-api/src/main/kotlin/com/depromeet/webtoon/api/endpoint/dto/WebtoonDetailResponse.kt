package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay

data class WebtoonDetailResponse(
    var id: Long,
    var title: String,
    var thumbnail: String,
    var authors: MutableList<Author>,
    var site: WebtoonSite,
    var weekday: MutableList<WeekDay>,
    var summary: String,
    // todo review 정보추가 ( storyScore, drawingScore, comment )
)
