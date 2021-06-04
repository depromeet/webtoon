/* todo
package com.depromeet.webtoon.api.endpoint.comment.service

import com.depromeet.webtoon.api.endpoint.comment.dto.CreateCommentRequest
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.comment.commentFixture
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.mockito.Mockito
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContext
import java.time.LocalDateTime
import java.util.*

class CommentImportServiceTest: FunSpec({

    lateinit var commentImportService: CommentImportService
    lateinit var commentRepository: CommentRepository
    lateinit var webtoonRepository: WebtoonRepository
    lateinit var accountRepository: AccountRepository
    lateinit var authentication: Authentication
    lateinit var securityContext: SecurityContext



    beforeTest {
        commentRepository = mockk()
        webtoonRepository = mockk()
        accountRepository = mockk()
        authentication = Mockito.mock(Authentication::class.java)
        securityContext = Mockito.mock(SecurityContext::class.java)
        commentImportService = CommentImportService(commentRepository, webtoonRepository, accountRepository)
    }

    context("CommentImportServiceTest"){

        test("createComment"){

            // given
            securityContext.authentication = authentication
            SecurityContextHolder.setContext(securityContext)
            Mockito.`when`(authentication.name).thenReturn("1")
            val account = accountFixture(1L)
            val webtoon = webtoonFixture(1L)
            val newComment = commentFixture(1L, "funny", account, webtoon, account.nickname, LocalDateTime.now(), LocalDateTime.now())

            every { accountRepository.findById(any()) } returns Optional.of(account)
            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)


            // when
            commentImportService.createComment(CreateCommentRequest(webtoon.id!!, "funny"))

            verify(exactly = 1) {
                accountRepository.findById(account.id!!)
                webtoonRepository.findById(webtoon.id!!)
            }


        }
    }

})
*/
