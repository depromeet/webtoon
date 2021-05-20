package com.depromeet.webtoon.api.endpoint.comment.dto

import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.comment.model.Comment
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("댓글 생성 및 수정 DTO")
data class CommentRequest(
    @ApiModelProperty("사용자ID")
    val accountId: Long,
    @ApiModelProperty("사용자닉네임")
    val nickname: String,
    @ApiModelProperty("웹툰ID")
    val webtoonId: Long,
    @ApiModelProperty("댓글내용")
    val content: String
)

fun convertRequestToComment(commentRequest: CommentRequest): Comment {
    return Comment(
        webtoon = Webtoon(commentRequest.webtoonId),
        account = Account(commentRequest.accountId),
        content = commentRequest.content,
        nickname = commentRequest.nickname
    )
}
