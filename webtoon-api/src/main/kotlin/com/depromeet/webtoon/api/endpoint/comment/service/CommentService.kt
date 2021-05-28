package com.depromeet.webtoon.api.endpoint.comment.service

import com.depromeet.webtoon.api.endpoint.comment.dto.CommentsResponse
import com.depromeet.webtoon.api.endpoint.comment.dto.convertToCommentsResponse
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService (val commentRepository: CommentRepository) {

    fun deleteComment(id: Long) {
        // todo 권한 확인
        commentRepository.deleteById(id)
    }

    fun getComments(webtoonId: Long, commentId: Long?, pageSize: Long): ApiResponse<CommentsResponse>{
        val comments = commentRepository.getComments(webtoonId, commentId, pageSize)
        return if(comments[comments.size-1].id == 1L){
            ApiResponse.ok(convertToCommentsResponse(comments, null))
        } else{
            ApiResponse.ok(convertToCommentsResponse(comments, comments[comments.size-1].id!!))
        }


    }
}
