package com.depromeet.webtoon.core.domain.comment.service

import com.depromeet.webtoon.api.endpoint.comment.service.CommentService
import com.depromeet.webtoon.core.domain.comment.commentFixture
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class CommentServiceTest: FunSpec ({

    lateinit var commentRepository: CommentRepository
    lateinit var commentService: CommentService

    beforeTest {
        commentRepository = mockk()
        commentService = CommentService(commentRepository)
    }

    context("CommentServiceTest"){
        test("deleteComment"){

            //given todo void return 하는경우 어떻게하는지?
            val comment = commentFixture(1L)
            every { commentRepository.deleteById(any()) }.returns(Unit)

            // when
            commentService.deleteComment(comment.id!!)

            verify { commentRepository.deleteById(comment.id!!) }
        }

        test("getComments"){
            //given
            every { commentRepository.getComments(any(), any(), any()) }.returns(listOf(commentFixture(id=4L), commentFixture(id = 3L)))

            //when
            commentService.getComments(1L, 5L, 2)

            verify(exactly = 1) { commentRepository.getComments(1L, 5L, 2) }
        }
    }

})

