package com.depromeet.webtoon.api.endpoint.home.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("상단배너 웹툰정보")
data class TopBannerItem(
    @ApiModelProperty(value = "웹툰 공통 리스폰스")
    val webtoon: WebtoonResponse,
    @ApiModelProperty(value = "배너 내부 꾸밈 텍스트")
    val caption: String,
)
