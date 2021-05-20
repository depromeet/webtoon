package com.depromeet.webtoon.api.endpoint.comment.service

import com.depromeet.webtoon.api.endpoint.comment.dto.CommentRequest
import com.depromeet.webtoon.api.endpoint.comment.dto.CommentUpsertResponse
import com.depromeet.webtoon.api.endpoint.comment.dto.convertRequestToComment
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = false)
class CommentImportService(
    val commentRepository: CommentRepository,
    val webtoonRepository: WebtoonRepository,
    val accountRepository: AccountRepository,
) {

    fun upsertComment(commentRequest: CommentRequest): ApiResponse<CommentUpsertResponse> {

        if(webtoonRepository.findById(commentRequest.webtoonId).isEmpty){
            throw ApiValidationException("잘못된 웹툰ID 입니다")
        }

        if(accountRepository.findById(commentRequest.accountId).isEmpty){
            throw ApiValidationException("잘못된 사용자ID 입니다.")
        }

        val optionalComment = commentRepository.findByWebtoonIdAndAccountId(commentRequest.webtoonId, commentRequest.accountId)

        if(optionalComment != null){    // 수정
            optionalComment.apply {
                content = commentRequest.content
            }
            return ApiResponse.ok(
                CommentUpsertResponse(
                    optionalComment.account.id!!,
                    optionalComment.webtoon.id!!,
                    optionalComment.content!!,
                    optionalComment.nickname!!
                )
            )
        } else {                        // 생성
            val newComment = convertRequestToComment(commentRequest).let { commentRepository.save(it) }
            return ApiResponse.ok(
                CommentUpsertResponse(
                    newComment.account.id!!,
                    newComment.webtoon.id!!,
                    newComment.content!!,
                    newComment.nickname!!
                )
            )
        }


    }
}
