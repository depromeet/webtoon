package com.depromeet.webtoon.core.application.api.dto

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.type.BackgroundColor
import com.depromeet.webtoon.core.type.WeekDay
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("요일별 웹툰")
data class
WebtoonResponse(
    @ApiModelProperty("웹툰ID")
    var id: Long,
    @ApiModelProperty("사이트")
    var site: String,
    @ApiModelProperty("제목")
    var title: String,
    @ApiModelProperty(value = "줄거리")
    var summary: String,
    @ApiModelProperty("작가")
    var authors: List<AuthorResponse>,
    @ApiModelProperty("썸네일 이미지")
    var thumbnail: String,
    @ApiModelProperty("연재날짜")
    var weekday: List<WeekDay>,
    @ApiModelProperty(value = "웹툰 url")
    var url: String,
    @ApiModelProperty("플랫폼 평점")
    var score: Double,
    @ApiModelProperty("웹툰 장르")
    var genres: List<String>,
    @ApiModelProperty("웹툰 배경화면")
    var backgroundColor: BackgroundColor,
    @ApiModelProperty("웹툰 연재중 여부")
    var isComplete: Boolean,

)

fun Webtoon.convertToWebtoonResponse() = WebtoonResponse(
    this.id!!,
    this.site.name,
    this.title,
    this.summary,
    this.authors.map { AuthorResponse(it.id!!, it.name) },
    this.thumbnail,
    this.weekdays,
    this.url,
    this.score,
    this.genres,
    this.backgroudColor,
    this.isComplete!!,
)

fun List<Webtoon>.convertToWebtoonResponses() = this.map { it.convertToWebtoonResponse() }
