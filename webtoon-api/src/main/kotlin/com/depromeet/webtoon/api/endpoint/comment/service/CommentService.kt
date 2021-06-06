package com.depromeet.webtoon.api.endpoint.comment.service

import com.depromeet.webtoon.api.endpoint.comment.dto.CommentsResponse
import com.depromeet.webtoon.api.endpoint.comment.dto.convertToCommentsResponse
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService (val commentRepository: CommentRepository) {

    fun getComments(webtoonId: Long, commentId: Long?, pageSize: Long): CommentsResponse{
        val comments = commentRepository.getComments(webtoonId, commentId, pageSize)

        if(comments.isEmpty()){
            return convertToCommentsResponse(null, null)
        }

        return if(comments[comments.size-1].id == 1L){
            convertToCommentsResponse(comments, null)
        } else{
            convertToCommentsResponse(comments, comments[comments.size-1].id!!)
        }
    }
}
