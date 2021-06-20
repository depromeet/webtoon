package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.api.endpoint.comment.dto.CommentResponse
import com.depromeet.webtoon.api.endpoint.comment.dto.CommentsResponse
import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponse
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.domain.account.dto.convertToAccountResponse
import com.depromeet.webtoon.core.domain.comment.model.Comment
import com.depromeet.webtoon.core.domain.rating.dto.CommentDto
import com.depromeet.webtoon.core.domain.rating.dto.ScoreDto
import com.depromeet.webtoon.core.domain.rating.model.Rating
import com.depromeet.webtoon.core.domain.rating.model.WebtoonRatingAverage
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("웹툰 상세")
data class WebtoonDetailResponse(
    @ApiModelProperty("웹툰 기본정보")
    val webtoon: WebtoonResponse,
    @ApiModelProperty(value = "toonietoonie평점")
    val toonieScore: ScoreResponse,
    @ApiModelProperty(value = "댓글")
    val comments: List<CommentResponse>?,
    @ApiModelProperty(value = "랜덤 추천")
    val randomRecommendWebtoons: List<WebtoonResponse>,

)

fun convertToWebtoonDetailResponse (webtoon: Webtoon, rating: WebtoonRatingAverage?, comments: List<Comment>?, randomWebtoons: List<Webtoon>): WebtoonDetailResponse{
    return WebtoonDetailResponse(
        webtoon = webtoon.convertToWebtoonResponse(),

        if (rating != null){
            ScoreResponse(rating.totalAverage!!, rating.totalStoryScore!!, rating.totalDrawingScore!!)
        } else {
            ScoreResponse(0.0, 0.0, 0.0)
        },
        comments = comments?.map { CommentResponse(it.id!!, it.account.convertToAccountResponse(), it.content!!, it.modifiedAt!!.toLocalDate()) },
        randomRecommendWebtoons = randomWebtoons.convertToWebtoonResponses()
    )
}
