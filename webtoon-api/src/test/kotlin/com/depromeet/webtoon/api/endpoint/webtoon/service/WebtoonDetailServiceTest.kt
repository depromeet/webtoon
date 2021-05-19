package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.rating.dto.ScoreDto
import com.depromeet.webtoon.core.domain.rating.repository.RatingRepository
import com.depromeet.webtoon.core.domain.rating.reviewFixture
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class WebtoonDetailServiceTest : FunSpec({

    lateinit var webtoonDetailService: WebtoonDetailService
    lateinit var webtoonRepository: WebtoonRepository
    lateinit var ratingRepository: RatingRepository

    beforeTest {
        webtoonRepository = mockk()
        ratingRepository = mockk()
        webtoonDetailService = WebtoonDetailService(webtoonRepository, ratingRepository)
    }

    context("WebtoonDetailService") {
        test("getWebtoonDetail 정상수행") {

            // given
            val author = authorFixture(id = 1L)
            val webtoon = webtoonFixture(id = 1L, authors = listOf(author))
            val account = accountFixture()
            val review = reviewFixture(webtoon = webtoon, account = account)

            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)
            every { ratingRepository.findByWebtoon(any()) } returns review
            every { ratingRepository.getScores(any()) } returns ScoreDto(review.storyScore, review.drawingScore)

            // when
            webtoonDetailService.getWebtoonDetail(1L)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(1L)
            }

            verify(exactly = 1) {
                ratingRepository.findByWebtoon(
                    withArg {
                        it shouldBe webtoon
                    }
                )
            }

            verify(exactly = 1) {
                ratingRepository.getScores(
                    withArg {
                        it shouldBe webtoon
                    }
                )
            }
        }

        test("getWebtoonDetail 존재하지 않는 웹툰 id") {

            every { webtoonRepository.findById(any()) } returns Optional.empty()

            // when & then
            shouldThrow<ApiValidationException> { webtoonDetailService.getWebtoonDetail(2L) }
        }

        test("getWebtoonDetail 웹툰에 대한 리뷰가 없는 경우") {
            // given
            val author = authorFixture(id = 1L)
            val webtoon = webtoonFixture(id = 1L, authors = listOf(author))

            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)
            every { ratingRepository.findByWebtoon(any()) } returns null

            // when
            val webtoonDetail = webtoonDetailService.getWebtoonDetail(1L)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(1L)
            }

            verify(exactly = 1) {
                ratingRepository.findByWebtoon(
                    withArg {
                        it shouldBe webtoon
                    }
                )
            }

            webtoonDetail.data!!.score.storyScore.shouldBe(0.0)
            webtoonDetail.data!!.score.drawingScore.shouldBe(0.0)
        }
    }
})
