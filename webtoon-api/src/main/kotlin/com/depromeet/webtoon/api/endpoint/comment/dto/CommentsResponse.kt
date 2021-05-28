package com.depromeet.webtoon.api.endpoint.comment.dto

import com.depromeet.webtoon.core.domain.comment.model.Comment
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate

@ApiModel("댓글")
data class CommentsResponse(
    @ApiModelProperty("남은 댓글 존재 여부")
    val isLastComment: Boolean,
    @ApiModelProperty("마지막 댓글 ID")
    val lastCommentId: Long?,
    @ApiModelProperty("댓글")
    val commentInfo: List<CommentInfo>,
)

data class CommentInfo(
    @ApiModelProperty("댓글 Id")
    val commentId: Long,
    @ApiModelProperty("작성자 닉네임")
    val nickname: String,
    @ApiModelProperty("댓글 내용")
    val content: String,
    @ApiModelProperty("작성 날짜")
    val writeDate: LocalDate,
)

fun convertToCommentsResponse(comments: List<Comment>, lastCommentId: Long?) :CommentsResponse{
    if(lastCommentId == null){
        return CommentsResponse(
            isLastComment = true,
            lastCommentId = null,
            commentInfo = comments.map { CommentInfo(it.id!!, it.nickname!!, it.content!!, it.modifiedAt!!.toLocalDate()) }
        )
    } else {
        return CommentsResponse(
            isLastComment = false,
            lastCommentId = lastCommentId,
            commentInfo = comments.map { CommentInfo(it.id!!, it.nickname!!, it.content!!, it.modifiedAt!!.toLocalDate()) }
        )
    }
}

