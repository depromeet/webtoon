package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.core.domain.review.dto.ScoreDto
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
            val webtoon = webtoonFixture(id = id)

            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)

            every { reviewRepository.getScores(any()) } returns ScoreDto(3.0, 3.5)
            every { reviewRepository.getComments(any()) } returns listOf("재밌다", "재미없다")

            // when
            webtoonDetailService.getWebtoonDetail(id)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(id)
            }

            verify(exactly = 1) {
                reviewRepository.getComments(
                    withArg {
                        it shouldBe webtoon
                    }
                )
            }

            verify(exactly = 1) {
                reviewRepository.getComments(
                    withArg {
                        it shouldBe webtoon
                    }
                )
            }
        }
    }
})
