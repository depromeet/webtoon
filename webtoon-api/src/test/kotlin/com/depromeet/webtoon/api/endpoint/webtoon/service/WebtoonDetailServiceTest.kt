package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.review.dto.CommentDto
import com.depromeet.webtoon.core.domain.review.dto.ScoreDto
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import com.depromeet.webtoon.core.domain.review.reviewFixture
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
            val author = authorFixture(id = 1L)
            val webtoon = webtoonFixture(id = 1L, authors = listOf(author))
            val account = accountFixture()
            val review = reviewFixture(webtoon = webtoon, account = account)

            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)
            every { reviewRepository.findByWebtoon(any()) } returns Optional.of(review)
            every { reviewRepository.getScores(any()) } returns ScoreDto(review.storyScore, review.drawingScore)
            every { reviewRepository.getComments(any()) } returns listOf(CommentDto(review.comment, account.nickname))

            // when
            webtoonDetailService.getWebtoonDetail(1L)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(1L)
            }

            verify(exactly = 1) {
                reviewRepository.findByWebtoon(
                    withArg {
                        it shouldBe webtoon
                    }
                )
            }

            verify(exactly = 1) {
                reviewRepository.getScores(
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
