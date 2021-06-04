package com.depromeet.webtoon.api.endpoint.comment.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("댓글 수정 DTO")
data class UpdateCommentRequest(
    @ApiModelProperty("댓글 Id")
    val commentId: Long,

    @ApiModelProperty("댓글내용")
    val content: String
)
