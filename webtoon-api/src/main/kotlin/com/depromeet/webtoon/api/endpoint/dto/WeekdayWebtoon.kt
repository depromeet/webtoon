package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("요일별 웹툰")
data class WeekdayWebtoon(
    @ApiModelProperty("웹툰ID")
    var id: Long,
    @ApiModelProperty("사이트")
    var site: String,
    @ApiModelProperty("제목")
    var title: String,
    @ApiModelProperty("작가")
    var author: List<String>,
    @ApiModelProperty("(특정요일) 인기순위")
    var popularity: Int,
    @ApiModelProperty("썸네일 이미지")
    var thumbnail: String
)

fun Webtoon.convertToWeekDayWebtoon() = WeekdayWebtoon(
    this.id!!,
    this.site.name,
    this.title,
    this.authors.map { it.name },
    this.popularity,
    this.thumbnail
)

fun List<Webtoon>.convertToWeekDayWebtoons() = this.map { it.convertToWeekDayWebtoon() }
