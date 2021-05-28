package com.depromeet.webtoon.core.domain.comment.repository

import com.depromeet.webtoon.core.domain.comment.model.Comment

interface CommentCustomRepository {
    fun getComments(commentId: Long?, pageSize: Long): List<Comment>
}
