package com.depromeet.webtoon.api.endpoint.comment.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class CommentUpsertResponse(
    @ApiModelProperty("사용자ID")
    val accountId: Long,
    @ApiModelProperty("웹툰ID")
    val webtoonId: Long,
    @ApiModelProperty(value = "사용자 닉네임")
    val nickname: String,
    @ApiModelProperty("댓글내용")
    val content: String
)
