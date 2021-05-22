package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.api.type.ApiResponseStatus
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.comment.commentFixture
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import com.depromeet.webtoon.core.domain.rating.repository.WebtoonRatingAverageRepository
import com.depromeet.webtoon.core.domain.rating.webtoonRatingAverageFixture
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

class WebtoonDetailServiceTest : FunSpec({

    lateinit var webtoonDetailService: WebtoonDetailService
    lateinit var webtoonRepository: WebtoonRepository
    lateinit var webtoonRatingAverageRepository: WebtoonRatingAverageRepository
    lateinit var commentRepository: CommentRepository



    beforeTest {
        webtoonRepository = mockk()
        webtoonRatingAverageRepository = mockk()
        commentRepository = mockk()
        webtoonDetailService = WebtoonDetailService(webtoonRepository, webtoonRatingAverageRepository, commentRepository)
    }

    context("WebtoonDetailService") {
        test("getWebtoonDetail 정상수행") {

            // given
            val author = authorFixture(id = 1L)
            val webtoon = webtoonFixture(id = 1L, authors = listOf(author))
            val account = accountFixture(id = 1L)
            val ratingAverage = webtoonRatingAverageFixture(id = 1L, webtoon = webtoon)
            val comment = commentFixture(1L, webtoon = webtoon, account = account)


            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)
            every { webtoonRatingAverageRepository.findByWebtoonId(any()) } returns ratingAverage
            every { commentRepository.findAllByWebtoonId(any()) } returns listOf(comment)


            // when
            val webtoonDetail = webtoonDetailService.getWebtoonDetail(1L)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(1L)
            }

            verify(exactly = 1) {
                webtoonRatingAverageRepository.findByWebtoonId(1L)
            }

            verify(exactly = 1) {
                commentRepository.findAllByWebtoonId(1L)
            }

            webtoonDetail.status.shouldBe(ApiResponseStatus.OK)
            webtoonDetail.data!!.score.totalScore.shouldBe(ratingAverage.totalAverage)
            webtoonDetail.data!!.score.storyScore.shouldBe(ratingAverage.storyAverage)
            webtoonDetail.data!!.score.drawingScore.shouldBe(ratingAverage.drawingAverage)
            webtoonDetail.data!!.authors.size.shouldBe(1)
            webtoonDetail.data!!.comments.size.shouldBe(1)
        }

        test("getWebtoonDetail 존재하지 않는 웹툰 id") {

            every { webtoonRepository.findById(any()) } returns Optional.empty()

            // when & then
            shouldThrow<ApiValidationException> { webtoonDetailService.getWebtoonDetail(2L) }
        }

        test("getWebtoonDetail 웹툰에 대한 평가가 없는 경우") {
            // given
            val author = authorFixture(id = 1L)
            val webtoon = webtoonFixture(id = 1L, authors = listOf(author))
            val account = accountFixture(id = 1L)
            val comment = commentFixture(1L, webtoon = webtoon, account = account)

            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)
            every { webtoonRatingAverageRepository.findByWebtoonId(any()) } returns null
            every { commentRepository.findAllByWebtoonId(any()) } returns listOf(comment)

            // when
            val webtoonDetail = webtoonDetailService.getWebtoonDetail(1L)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(1L)
            }

            verify(exactly = 1) {
                webtoonRatingAverageRepository.findByWebtoonId(1L)
            }

            verify(exactly = 1) {
                commentRepository.findAllByWebtoonId(1L)
            }

            webtoonDetail.data!!.score.totalScore.shouldBe(0.0)
            webtoonDetail.data!!.score.storyScore.shouldBe(0.0)
            webtoonDetail.data!!.score.drawingScore.shouldBe(0.0)
            webtoonDetail.data!!.comments.size.shouldBe(1)
        }

        test("getWebtoonDetail 웹툰에 대한 댓글이 없는 경우"){
            // given
            val author = authorFixture(id = 1L)
            val webtoon = webtoonFixture(id = 1L, authors = listOf(author))
            val ratingAverage = webtoonRatingAverageFixture(id = 1L, webtoon = webtoon)

            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)
            every { webtoonRatingAverageRepository.findByWebtoonId(any()) } returns ratingAverage
            every { commentRepository.findAllByWebtoonId(any()) } returns emptyList()

            val webtoonDetail = webtoonDetailService.getWebtoonDetail(1L)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(1L)
            }

            verify(exactly = 1) {
                webtoonRatingAverageRepository.findByWebtoonId(1L)
            }

            verify(exactly = 1) {
                commentRepository.findAllByWebtoonId(1L)
            }

            webtoonDetail.status.shouldBe(ApiResponseStatus.OK)
            webtoonDetail.data!!.score.totalScore.shouldBe(ratingAverage.totalAverage)
            webtoonDetail.data!!.score.storyScore.shouldBe(ratingAverage.storyAverage)
            webtoonDetail.data!!.score.drawingScore.shouldBe(ratingAverage.drawingAverage)
            webtoonDetail.data!!.authors.size.shouldBe(1)
            webtoonDetail.data!!.comments.size.shouldBe(0)

        }

    }
})
