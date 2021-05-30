package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import com.depromeet.webtoon.core.domain.rating.dto.CommentDto
import com.depromeet.webtoon.core.domain.rating.dto.ScoreDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("웹툰 상세")
data class WebtoonDetailResponse(
    @ApiModelProperty("웹툰 기본정보")
    val webtoon: WebtoonResponse,
    @ApiModelProperty(value = "toonietoonie평점")
    val toonieScore: ScoreResponse,
    @ApiModelProperty(value = "댓글")
    val comments: List<CommentDto>
)

fun convertToWebtoonDetailResponse(webtoon: WebtoonResponse, scores: ScoreDto, comments: List<CommentDto>): WebtoonDetailResponse {
    return WebtoonDetailResponse(
        webtoon = webtoon,
        ScoreResponse(scores.totalScore!!, scores.storyScore!!, scores.drawingScore!!),
        comments = comments
    )
}
