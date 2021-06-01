package com.depromeet.webtoon.core.domain.webtoon.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty


@ApiModel("장르별 탑20 반환 데이터")
data class WebtoonTop20Response (
    @ApiModelProperty("장르")
    val genre: String,
    @ApiModelProperty("TOP20 웹툰 리스트")
    val top20Webtoons: List<WebtoonResponse>,


)
