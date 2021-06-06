package com.depromeet.webtoon.core.domain.account.dto

import com.depromeet.webtoon.core.domain.account.model.Account

data class AccountResponse(
    val accountId :Long,
    val nickname :String?,
    val profileImage :String?,
)

fun Account.convertToAccountResponse(): AccountResponse{
    return AccountResponse(
        this.id!!,
        this.nickname,
        this.profileImage
    )
}
