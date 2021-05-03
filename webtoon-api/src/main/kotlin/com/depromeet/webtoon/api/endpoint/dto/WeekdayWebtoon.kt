package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon

data class WeekdayWebtoon(
    var id: Long,
    var site: String,
    var title: String,
    var author: List<Author>,
    var popularity: Int,
    var thumbnail: String
)

fun Webtoon.convertToWeekDayWebtoon() = WeekdayWebtoon(
    this.id!!,
    this.site.name,
    this.title,
    this.authors,
    this.popularity,
    this.thumbnail
)

fun List<Webtoon>.convertToWeekDayWebtoons() = this.map { it.convertToWeekDayWebtoon() }
