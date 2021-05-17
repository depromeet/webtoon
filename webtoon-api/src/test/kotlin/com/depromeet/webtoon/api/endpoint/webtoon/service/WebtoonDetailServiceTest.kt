package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.review.dto.CommentDto
import com.depromeet.webtoon.core.domain.review.dto.ScoreDto
import com.depromeet.webtoon.core.domain.review.model.Review
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import java.util.*

class WebtoonDetailServiceTest : FunSpec({

    lateinit var webtoonDetailService: WebtoonDetailService
    lateinit var webtoonRepository: WebtoonRepository
    lateinit var reviewRepository: ReviewRepository

    beforeTest {
        webtoonRepository = mockk()
        reviewRepository = mockk()
        webtoonDetailService = WebtoonDetailService(webtoonRepository, reviewRepository)
    }

    context("WebtoonDetailService") {
        test("getWebtoonDetail") {

            // given
            val id = 1L

            every { reviewRepository.findByWebtoon(any()) } returns Optional.of(Review(1L, webtoonFixture(), accountFixture(), "제밌다", 3.0, 5.0, LocalDateTime.now(), LocalDateTime.now()))
            every { webtoonRepository.findById(any()) } returns Optional.of(webtoonFixture())
            every { reviewRepository.getScores(any()) } returns ScoreDto(3.0, 3.5)
            every { reviewRepository.getComments(any()) } returns listOf(CommentDto("재밌다", "tester"))

            // when
            webtoonDetailService.getWebtoonDetail(id)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(id)
            }

            verify(exactly = 1) {
                reviewRepository.getComments(
                    withArg {
                        it shouldBe webtoonFixture()
                    }
                )
            }

            verify(exactly = 1) {
                reviewRepository.getComments(
                    withArg {
                        it shouldBe webtoonFixture()
                    }
                )
            }
        }
    }
})
