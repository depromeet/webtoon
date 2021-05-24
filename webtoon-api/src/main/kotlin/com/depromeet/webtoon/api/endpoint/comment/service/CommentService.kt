package com.depromeet.webtoon.api.endpoint.comment.service

import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService (val commentRepository: CommentRepository) {

    fun deleteComment(id: Long) {
        // todo 권한 확인
        commentRepository.deleteById(id)
    }
}
