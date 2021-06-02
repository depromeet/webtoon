package com.depromeet.webtoon.core.domain.account.dto

import com.depromeet.webtoon.core.domain.account.model.Account
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("사용자 정보")
data class AccountLoginResult(
    @ApiModelProperty("닉네임")
    val nickname: String
)

// todo 하드코드로 넣는게 아니라 account 의 자동생성한 nickname 넣어줄것
fun Account.convertToAccountLoginResult():AccountLoginResult{
    return AccountLoginResult(nickname = "testNick")
}
