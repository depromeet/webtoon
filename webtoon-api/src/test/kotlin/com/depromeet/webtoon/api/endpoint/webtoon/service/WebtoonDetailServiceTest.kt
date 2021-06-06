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


    //todo RandomWebtoon

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
            every { webtoonRatingAverageRepository.findByWebtoon(any()) } returns ratingAverage
            every { commentRepository.findTop5ByWebtoonOrderByCreatedAtDesc(any()) } returns listOf(comment)
            every { webtoonRepository.findRandomWebtoons() } returns emptyList()


            // when
            val webtoonDetail = webtoonDetailService.getWebtoonDetail(1L)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(1L)
                webtoonRatingAverageRepository.findByWebtoon(webtoon)
                commentRepository.findTop5ByWebtoonOrderByCreatedAtDesc(webtoon)
                webtoonRepository.findRandomWebtoons()
            }


            webtoonDetail.status.shouldBe(ApiResponseStatus.OK)
            webtoonDetail.data!!.webtoon.backgroundColor.shouldBe(webtoonFixture().backgroudColor)
            webtoonDetail.data!!.webtoon.score.shouldBe(webtoonFixture().score)
            webtoonDetail.data!!.webtoon.isComplete.shouldBe(webtoonFixture().isComplete)
            webtoonDetail.data!!.toonieScore.totalScore.shouldBe(ratingAverage.totalAverage)
            webtoonDetail.data!!.toonieScore.storyScore.shouldBe(ratingAverage.storyAverage)
            webtoonDetail.data!!.toonieScore.drawingScore.shouldBe(ratingAverage.drawingAverage)
            webtoonDetail.data!!.webtoon.authors.size.shouldBe(1)
            webtoonDetail.data!!.comments!!.size.shouldBe(1)
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
            every { webtoonRatingAverageRepository.findByWebtoon(any()) } returns null
            every { commentRepository.findTop5ByWebtoonOrderByCreatedAtDesc(any()) } returns listOf(comment)
            every { webtoonRepository.findRandomWebtoons() } returns emptyList()

            // when
            val webtoonDetail = webtoonDetailService.getWebtoonDetail(1L)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(1L)
                webtoonRatingAverageRepository.findByWebtoon(webtoon)
                commentRepository.findTop5ByWebtoonOrderByCreatedAtDesc(webtoon)
                webtoonRepository.findRandomWebtoons()
            }

            webtoonDetail.data!!.toonieScore.totalScore.shouldBe(0.0)
            webtoonDetail.data!!.toonieScore.storyScore.shouldBe(0.0)
            webtoonDetail.data!!.toonieScore.drawingScore.shouldBe(0.0)
            webtoonDetail.data!!.comments!!.size.shouldBe(1)
        }

        test("getWebtoonDetail 웹툰에 대한 댓글이 없는 경우"){
            // given
            val author = authorFixture(id = 1L)
            val webtoon = webtoonFixture(id = 1L, authors = listOf(author))
            val ratingAverage = webtoonRatingAverageFixture(id = 1L, webtoon = webtoon)

            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)
            every { webtoonRatingAverageRepository.findByWebtoon(any()) } returns ratingAverage
            every { commentRepository.findTop5ByWebtoonOrderByCreatedAtDesc(any()) } returns emptyList()
            every { webtoonRepository.findRandomWebtoons() } returns emptyList()

            val webtoonDetail = webtoonDetailService.getWebtoonDetail(1L)

            // then
            verify(exactly = 1) {
                webtoonRepository.findById(1L)
                webtoonRatingAverageRepository.findByWebtoon(webtoon)
                commentRepository.findTop5ByWebtoonOrderByCreatedAtDesc(webtoon)
                webtoonRepository.findRandomWebtoons()
            }

            webtoonDetail.status.shouldBe(ApiResponseStatus.OK)
            webtoonDetail.data!!.toonieScore.totalScore.shouldBe(ratingAverage.totalAverage)
            webtoonDetail.data!!.toonieScore.storyScore.shouldBe(ratingAverage.storyAverage)
            webtoonDetail.data!!.toonieScore.drawingScore.shouldBe(ratingAverage.drawingAverage)
            webtoonDetail.data!!.webtoon.authors.size.shouldBe(1)
            webtoonDetail.data!!.comments!!.size.shouldBe(0)

        }

    }
})
