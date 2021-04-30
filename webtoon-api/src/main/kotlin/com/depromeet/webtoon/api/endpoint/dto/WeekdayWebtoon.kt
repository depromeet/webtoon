package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.domain.author.model.Author

data class WeekdayWebtoon(
    var id: Long,
    var site: String,
    var title: String,
    var author: List<Author>,
    var popularity: Int,
    var thumbnail: String
)
