package com.depromeet.webtoon.api.endpoint.rating.dto

import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.rating.model.Rating
import com.depromeet.webtoon.core.domain.rating.model.WebtoonRatingAverage
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("평점 매기기 요청 DTO")
data class VoteRequest(
    @ApiModelProperty("회원 ID")
    val accountId: Long,
    @ApiModelProperty(value = "웹툰 ID")
    val webtoonId: Long,
    @ApiModelProperty("스토리 점수")
    val storyScore: Double,
    @ApiModelProperty("작화 점수")
    val drawingScore: Double
)

fun convertVoteRequestToRating(voteRequest: VoteRequest): Rating {
    return Rating(
        webtoon = Webtoon(voteRequest.webtoonId),
        account = Account(voteRequest.accountId),
        storyScore = voteRequest.storyScore,
        drawingScore = voteRequest.drawingScore,
    )
}

fun convertVoteRequestToRatingAvg(voteRequest: VoteRequest): WebtoonRatingAverage {
    return WebtoonRatingAverage(
        webtoon = Webtoon(voteRequest.webtoonId),
        totalStoryScore = voteRequest.storyScore,
        totalDrawingScore = voteRequest.drawingScore,
        votes = 1,
        storyAverage = voteRequest.storyScore,
        drawingAverage = voteRequest.drawingScore,
        totalAverage = (voteRequest.storyScore + voteRequest.drawingScore)/2
    )
}
