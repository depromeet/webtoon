package com.depromeet.webtoon.api.endpoint.rating.service

import com.depromeet.webtoon.api.endpoint.rating.dto.VoteRequest
import com.depromeet.webtoon.api.endpoint.rating.vo.OriginScore
import com.depromeet.webtoon.api.type.ApiResponseStatus
import com.depromeet.webtoon.core.domain.rating.model.WebtoonRatingAverage
import com.depromeet.webtoon.core.domain.rating.repository.WebtoonRatingAverageRepository
import com.depromeet.webtoon.core.domain.rating.webtoonRatingAverageFixture
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime

class WebtoonRatingAverageServiceTest : FunSpec({

    lateinit var repository: WebtoonRatingAverageRepository
    lateinit var service: WebtoonRatingAverageService

    beforeTest {
        repository = mockk()
        service = WebtoonRatingAverageService(repository)
    }

    context("WebtoonRatingAverageService") {
        test("[ID-1]사용자가 [ID-2]웹툰에 대한 전체평가 중 최초인 경우") {
            // given
            val voteRequest = VoteRequest(1, 2, 5.0, 5.0)
            val originScore = OriginScore(false, voteRequest.storyScore, voteRequest.drawingScore)

            every { repository.findByWebtoonId(any()) } returns null
            every { repository.save(any()) } returns
                webtoonRatingAverageFixture(
                    id = 1,
                    webtoon = webtoonFixture(voteRequest.webtoonId),
                    totalStoryScore = voteRequest.storyScore,
                    totalDrawingScore = voteRequest.drawingScore,
                    votes = 1,
                    storyAverage = voteRequest.storyScore,
                    drawingAverage = voteRequest.drawingScore,
                    totalAverage = (voteRequest.storyScore + voteRequest.drawingScore) / 2
                )

            val result = service.upsertWebtoonScore(voteRequest, originScore)

            verify(exactly = 1) { repository.save(any()) }
            result.status.shouldBe(ApiResponseStatus.OK)
            result.data!!.storyAverage.shouldBe(voteRequest.storyScore)
            result.data!!.drawingAverage.shouldBe(voteRequest.drawingScore)
            result.data!!.votes.shouldBe(1)
            result.data!!.totalAverage.shouldBe((voteRequest.storyScore + voteRequest.drawingScore) / 2)
        }

        test("[ID-1]사용자의 [ID-2]웹툰에 대한 첫 평가 & 기존평가 존재하는 경우") {
            // given
            val voteRequest = VoteRequest(1, 2, 5.0, 5.0)
            val originScore = OriginScore(false, voteRequest.storyScore, voteRequest.drawingScore)

            every { repository.findByWebtoonId(any()) } returns
                WebtoonRatingAverage(
                    1,
                    webtoonFixture(voteRequest.webtoonId),
                    totalStoryScore = 1.0,
                    totalDrawingScore = 1.0,
                    votes = 1,
                    storyAverage = 1.0,
                    drawingAverage = 1.0,
                    totalAverage = 1.0,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )

            // when
            val result = service.upsertWebtoonScore(voteRequest, originScore)

            // then
            result.status.shouldBe(ApiResponseStatus.OK)
            result.data!!.storyAverage.shouldBe((1.0 + 5.0) / 2)
            result.data!!.drawingAverage.shouldBe((1.0 + 5.0) / 2)
            result.data!!.totalAverage.shouldBe(3.0)
            result.data!!.votes.shouldBe(2)
        }

        test("[ID-1]사용자의 [ID-2]웹툰에 대한 재평가") {
            // given
            val voteRequest = VoteRequest(1, 2, 5.0, 5.0)
            val originScore = OriginScore(true, 1.0, 1.0)

            every { repository.findByWebtoonId(any()) } returns
                WebtoonRatingAverage(
                    1,
                    webtoonFixture(voteRequest.webtoonId),
                    totalStoryScore = 1.0,
                    totalDrawingScore = 1.0,
                    votes = 1,
                    storyAverage = 1.0,
                    drawingAverage = 1.0,
                    totalAverage = 1.0,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )

            // when
            val result = service.upsertWebtoonScore(voteRequest, originScore)

            result.status.shouldBe(ApiResponseStatus.OK)
            result.data!!.storyAverage.shouldBe(voteRequest.storyScore)
            result.data!!.drawingAverage.shouldBe(voteRequest.drawingScore)
            result.data!!.totalAverage.shouldBe((voteRequest.storyScore + voteRequest.drawingScore) / 2)
            result.data!!.votes.shouldBe(1)
        }
    }
})
