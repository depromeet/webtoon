package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("요일별 웹툰")
data class WebtoonResponse(
    @ApiModelProperty("웹툰ID")
    var id: Long,
    @ApiModelProperty("사이트")
    var site: String,
    @ApiModelProperty("제목")
    var title: String,
    @ApiModelProperty("작가")
    var authors: List<AuthorResponse>,
    @ApiModelProperty("썸네일 이미지")
    var thumbnail: String
)

fun Webtoon.convertToWebtoonResponse() = WebtoonResponse(
    this.id!!,
    this.site.name,
    this.title,
    this.authors.map { AuthorResponse(it.id!!, it.name) },
    this.thumbnail
)

fun List<Webtoon>.convertToWebtoonResponses() = this.map { it.convertToWebtoonResponse() }
