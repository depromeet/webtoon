package com.depromeet.webtoon.api.endpoint.webtoon.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("작겨별 웹툰")
data class AuthorWebtoonResponse(
    @ApiModelProperty("작가 이름")
    val authorName: String,

    @ApiModelProperty("총 작품 수")
    val totalWebtoon: Int,

    @ApiModelProperty(value = "웹툰")
    val webtoons: List<WebtoonResponse>,

)
fun convertToAuthorWebtoonResponse(webtoons: List<Webtoon>) = AuthorWebtoonResponse(
    authorName = webtoons.first().authors.first().name,
    totalWebtoon = webtoons.size,
    webtoons = webtoons.convertToWebtoonResponses()
)
