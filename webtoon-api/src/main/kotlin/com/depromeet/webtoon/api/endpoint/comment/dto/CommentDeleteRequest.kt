package com.depromeet.webtoon.api.endpoint.comment.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("댓글삭제 요청")
data class CommentDeleteRequest(
    @ApiModelProperty("삭제할 댓글 id")
    val commentId: Long,
    @ApiModelProperty("삭제할 댓글 작성자 id")
    val accountId: Long
)
