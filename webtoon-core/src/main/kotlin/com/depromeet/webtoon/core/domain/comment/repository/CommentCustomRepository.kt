package com.depromeet.webtoon.core.domain.comment.repository

import com.depromeet.webtoon.core.domain.comment.model.Comment
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon

interface CommentCustomRepository {
    fun getComments(webtoonId: Long, commentId: Long?, pageSize: Long): List<Comment>
    fun findRecent5Comments(webtoon: Webtoon): List<Comment>
}
