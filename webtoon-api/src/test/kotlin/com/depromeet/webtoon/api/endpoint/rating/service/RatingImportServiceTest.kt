package com.depromeet.webtoon.api.endpoint.rating.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.rating.dto.VoteRequest
import com.depromeet.webtoon.api.endpoint.rating.dto.VoteResponse
import com.depromeet.webtoon.api.endpoint.rating.vo.OriginScore
import com.depromeet.webtoon.api.type.ApiResponseStatus
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class RatingImportServiceTest : FunSpec({

    lateinit var webtoonRepository: WebtoonRepository
    lateinit var accountRepository: AccountRepository
    lateinit var ratingService: RatingService
    lateinit var webtoonRatingAverageService: WebtoonRatingAverageService
    lateinit var ratingImportService: RatingImportService

    beforeTest {
        webtoonRepository = mockk()
        accountRepository = mockk()
        ratingService = mockk()
        webtoonRatingAverageService = mockk()
        ratingImportService = RatingImportService(webtoonRepository, accountRepository, ratingService, webtoonRatingAverageService)
    }

    context("RatingImportService") {
        test("연관 메소드 호출 확인") {
            // given
            val voteRequest = VoteRequest(1, 1, 5.0, 5.0)

            every { webtoonRepository.findById(any()) }.returns(Optional.of(webtoonFixture(id = voteRequest.webtoonId)))
            every { accountRepository.findById(any()) }.returns(Optional.of(accountFixture(id = voteRequest.accountId)))
            every { ratingService.upsertWebtoonScore(any()) }.returns(OriginScore(true, 3.0, 3.0))
            every { webtoonRatingAverageService.upsertWebtoonScore(any(), any()) }.returns(ApiResponse<VoteResponse>(ApiResponseStatus.OK, null, VoteResponse(4.0, 4.0, 4.0, 2)))

            // when
            ratingImportService.upsertWebtoonScore(voteRequest)

            // then
            verify(exactly = 1) { ratingService.upsertWebtoonScore(voteRequest) }
            verify(exactly = 1) { webtoonRatingAverageService.upsertWebtoonScore(voteRequest, OriginScore(true, 3.0, 3.0)) }
        }

        test("잘못된 웹툰ID가 입력값으로 들어온 경우") {
            // given
            val voteRequest = VoteRequest(1, 1, 5.0, 5.0)
            every { webtoonRepository.findById(any()) }.returns(Optional.empty())

            // when
            shouldThrow<ApiValidationException> { ratingImportService.upsertWebtoonScore(voteRequest) }
        }

        test("존재하지 않는 사용자ID가 입력값으로 들어온 경우") {
            // given
            val voteRequest = VoteRequest(1, 1, 5.0, 5.0)
            every { webtoonRepository.findById(any()) }.returns(Optional.of(webtoonFixture(id = voteRequest.webtoonId)))
            every { accountRepository.findById(any()) }.returns(Optional.empty())

            // when
            shouldThrow<ApiValidationException> { ratingImportService.upsertWebtoonScore(voteRequest) }
        }
    }
})
