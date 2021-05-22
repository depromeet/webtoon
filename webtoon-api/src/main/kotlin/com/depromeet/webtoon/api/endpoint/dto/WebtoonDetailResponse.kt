package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.application.api.dto.AuthorResponse
import com.depromeet.webtoon.core.domain.rating.dto.CommentDto
import com.depromeet.webtoon.core.domain.rating.dto.ScoreDto
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("웹툰 상세")
data class WebtoonDetailResponse(
    @ApiModelProperty(value = "웹툰ID")
    var id: Long,
    @ApiModelProperty(value = "제목")
    var title: String,
    @ApiModelProperty(value = "썸네일 이미지")
    var thumbnail: String,
    @ApiModelProperty(value = "웹툰 url")
    var url: String,
    @ApiModelProperty(value = "작가")
    var authors: List<AuthorResponse>,
    @ApiModelProperty(value = "장르")
    var genres: List<String>,
    @ApiModelProperty(value = "사이트")
    var site: WebtoonSite,
    @ApiModelProperty(value = "날짜")
    var weekday: List<WeekDay>,
    @ApiModelProperty(value = "줄거리")
    var summary: String,
    @ApiModelProperty(value = "toonietoonie평점")
    val score: ScoreResponse,
)

fun convertToWebtoonDetailResponse(webtoon: Webtoon, scores: ScoreDto, comments: List<CommentDto>): WebtoonDetailResponse {
    return WebtoonDetailResponse(
        webtoon.id!!,
        webtoon.title,
        webtoon.thumbnail,
        webtoon.url,
        webtoon.authors.map { AuthorResponse(it.id!!, it.name) },
        webtoon.genres,
        webtoon.site,
        webtoon.weekdays,
        webtoon.summary,
        ScoreResponse(scores.storyScore!!, scores.drawingScore!!),
    )
}
