package com.depromeet.webtoon.core.domain.account.dto

import com.depromeet.webtoon.core.domain.account.model.Account
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("닉네임 변경 반환 DTO")
data class NicknameUpdateResponse(
    @ApiModelProperty("accountId")
    val accountId: Long,
    @ApiModelProperty("변경 후 닉네임")
    val nickname: String?,
    @ApiModelProperty("프로필 이미지")
    val profileImage: String?,
)

fun Account.converToNicknameUpdateRespose(): NicknameUpdateResponse{
    return NicknameUpdateResponse(
        this.id!!,
        this.nickname,
        this.profileImage
    )
}
