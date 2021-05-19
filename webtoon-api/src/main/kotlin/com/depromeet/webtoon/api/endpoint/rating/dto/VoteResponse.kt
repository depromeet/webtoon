package com.depromeet.webtoon.api.endpoint.rating.dto

import com.depromeet.webtoon.core.domain.rating.model.WebtoonRatingAverage
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("평점")
data class VoteResponse(
    @ApiModelProperty("투니평점")
    val totalAverage: Double?,
    @ApiModelProperty("스토리점수")
    val storyAverage: Double?,
    @ApiModelProperty("작화점수")
    val drawingAverage: Double?,
    @ApiModelProperty("평가인원수")
    val votes: Long?
)

// 최초 평가
fun firstVote(voteRequest: VoteRequest): VoteResponse {
    return VoteResponse(
        (voteRequest.storyScore + voteRequest.drawingScore) / 2,
        voteRequest.storyScore,
        voteRequest.drawingScore,
        1
    )
}

// 이미 평가가 있는 경우
fun updateVote(originVote: WebtoonRatingAverage): VoteResponse{
    return VoteResponse(
        (originVote.storyAverage!! + originVote.drawingAverage!!) / 2,
        originVote.storyAverage,
        originVote.drawingAverage,
        originVote.votes!!
    )
}
