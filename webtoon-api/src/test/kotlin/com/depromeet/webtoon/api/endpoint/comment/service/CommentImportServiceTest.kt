package com.depromeet.webtoon.api.endpoint.comment.service

import com.depromeet.webtoon.api.endpoint.comment.dto.CommentRequest
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.comment.commentFixture
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class CommentImportServiceTest: FunSpec({

    lateinit var commentImportService: CommentImportService
    lateinit var commentRepository: CommentRepository
    lateinit var webtoonRepository: WebtoonRepository
    lateinit var accountRepository: AccountRepository


    beforeTest {
        commentRepository = mockk()
        webtoonRepository = mockk()
        accountRepository = mockk()
        commentImportService = CommentImportService(commentRepository, webtoonRepository, accountRepository)
    }

    context("CommentImportServiceTest"){
        test("upsertComment 연관 메소드 정상 수행 테스트"){
            // given
            val commentRequest = CommentRequest(
                accountId = 1L,
                nickname = "tester",
                webtoonId = 1L,
                content = "재밌다"
            )
            val account = accountFixture(commentRequest.accountId, commentRequest.nickname)
            val webtoon = webtoonFixture(commentRequest.webtoonId)

            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)
            every { accountRepository.findById(any()) } returns Optional.of(account)
            every { commentRepository.findByWebtoonIdAndAccountId(any(), any()) } returns commentFixture(
                id = 1L,
                content = commentRequest.content,
                account = account,
                webtoon = webtoon,
                nickname = account.nickname!!
            )

            // when
            val result = commentImportService.upsertComment(commentRequest)

            // then
            verify(exactly = 1) { webtoonRepository.findById(commentRequest.webtoonId)  }

            verify(exactly = 1) { accountRepository.findById(commentRequest.accountId) }

            verify(exactly = 1) { commentRepository.findByWebtoonIdAndAccountId(commentRequest.webtoonId, commentRequest.accountId) }

            result.data!!.accountId.shouldBe(account.id)
            result.data!!.webtoonId.shouldBe(webtoon.id)
            result.data!!.content.shouldBe(commentRequest.content)
            result.data!!.nickname.shouldBe(account.nickname)

        }

        test("upsertComment 잘못된 웹툰ID 입력"){
            val commentRequest = CommentRequest(
                accountId = 1L,
                nickname = "tester",
                webtoonId = 1L,
                content = "재밌다"
            )

            every { webtoonRepository.findById(any()) } returns Optional.empty()

            shouldThrow<ApiValidationException> { commentImportService.upsertComment(commentRequest) }
        }
    }
})
