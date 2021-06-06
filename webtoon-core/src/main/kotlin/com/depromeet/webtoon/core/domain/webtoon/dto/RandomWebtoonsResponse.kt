package com.depromeet.webtoon.core.domain.webtoon.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("랜덤웹툰")
data class RandomWebtoonsResponse(
    @ApiModelProperty("웹툰리스트")
    val webtoons: List<WebtoonResponse>
)

fun List<Webtoon>.convertToRandomWebtoonsResponse(): RandomWebtoonsResponse{
    return RandomWebtoonsResponse(
        webtoons = convertToWebtoonResponses()
    )
}
