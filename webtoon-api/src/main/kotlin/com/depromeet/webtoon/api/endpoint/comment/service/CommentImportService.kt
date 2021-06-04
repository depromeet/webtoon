package com.depromeet.webtoon.api.endpoint.comment.service

import com.depromeet.webtoon.api.endpoint.comment.dto.CreateCommentRequest
import com.depromeet.webtoon.api.endpoint.comment.dto.UpdateCommentRequest
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.comment.model.Comment
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = false)
class CommentImportService(
    val commentRepository: CommentRepository,
    val webtoonRepository: WebtoonRepository,
    val accountRepository: AccountRepository,
) {

    fun createComment(createRequest: CreateCommentRequest){
        importComment(createRequest)
    }

    fun updateComment(updateRequest: UpdateCommentRequest) {
        val comment = getComment(updateRequest)
        validUpdateAuthority(comment)
        comment.content = updateRequest.content
        commentRepository.save(comment)
    }

    fun deleteComment(commentId: Long) {
        val comment = getComment(commentId)
        validDeleteAuthority(comment)
        commentRepository.delete(comment)
    }

    private fun validDeleteAuthority(comment: Comment) {
        if (comment.account.id!! != getAccount().id) {
            throw ApiValidationException("삭제 권한이 없습니다.")
        }
    }


    private fun validUpdateAuthority(comment: Comment) {
        if (comment.account.id!! != getAccount().id) {
            throw ApiValidationException("수정 권한이 없습니다.")
        }
    }

    private fun getComment(updateRequest: UpdateCommentRequest) =
        commentRepository.findById(updateRequest.commentId).orElseThrow { ApiValidationException("잘못된 Comment Id 입니다") }


    private fun getComment(commentId: Long) =
        commentRepository.findById(commentId).orElseThrow { ApiValidationException("잘못된 CommentId 입니다") }


    private fun importComment(createRequest: CreateCommentRequest) {
        val account = getAccount()
        val webtoon = getWebtoon(createRequest)
        val newComment = Comment(null, createRequest.content, account, webtoon, account.nickname, null, null)
        commentRepository.save(newComment)
    }

    private fun getWebtoon(createRequest: CreateCommentRequest) =
        webtoonRepository.findById(createRequest.webtoonId).orElseThrow { ApiValidationException("잘못된 웹툰 아이디입니다") }

    private fun getAccount(): Account{
        val authentication = SecurityContextHolder.getContext().authentication

        val accountId = authentication.name
        return accountRepository.findById(accountId.toLong()).orElseThrow { ApiValidationException("서버오류") }
    }



}
