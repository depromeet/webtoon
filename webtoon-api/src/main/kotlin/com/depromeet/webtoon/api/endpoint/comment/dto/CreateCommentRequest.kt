package com.depromeet.webtoon.api.endpoint.comment.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("댓글 생성 요청")
data class CreateCommentRequest(
    @ApiModelProperty("웹툰ID")
    val webtoonId: Long,
    @ApiModelProperty("댓글내용")
    val content: String
)
