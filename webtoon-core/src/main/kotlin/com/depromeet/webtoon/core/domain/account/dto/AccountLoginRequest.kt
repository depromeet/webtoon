package com.depromeet.webtoon.core.domain.account.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("로그인 요청")
data class AccountLoginRequest(
    @ApiModelProperty("토큰(deviceId)")
    val loginToken: String
)
