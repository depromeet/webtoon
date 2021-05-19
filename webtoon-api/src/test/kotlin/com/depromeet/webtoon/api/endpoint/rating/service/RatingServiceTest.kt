package com.depromeet.webtoon.api.endpoint.rating.service

import com.depromeet.webtoon.api.endpoint.rating.dto.VoteRequest
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.rating.model.Rating
import com.depromeet.webtoon.core.domain.rating.repository.RatingRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime

class RatingServiceTest : FunSpec({

    lateinit var ratingRepository: RatingRepository
    lateinit var ratingService: RatingService

    beforeTest {
        ratingRepository = mockk()
        ratingService = RatingService(ratingRepository)
    }

    context("RatingService") {
        test("[ID - 1]사용자의 [ID - 2]웹툰 최초평가") {
            // given
            val voteRequest = VoteRequest(1, 2, 5.0, 5.0)
            every {
                ratingRepository.findByWebtoonIdAndAccountId(
                    voteRequest.webtoonId,
                    voteRequest.accountId
                )
            }.returns(null)
            every { ratingRepository.save(any()) } returns Rating(
                1,
                webtoonFixture(voteRequest.webtoonId),
                accountFixture(voteRequest.accountId),
                voteRequest.storyScore,
                voteRequest.drawingScore,
                LocalDateTime.now(),
                LocalDateTime.now()
            )

            // when
            val originScore = ratingService.upsertWebtoonScore(voteRequest)

            // then
            verify(exactly = 1) { ratingRepository.save(any()) }
            originScore.isUpdate.shouldBe(false)
            originScore.originStoryScore.shouldBe(voteRequest.storyScore)
            originScore.originDrawingScore.shouldBe(voteRequest.drawingScore)
        }

        test("[ID - 1]사용자의 [ID -2]웹툰 평가수정") {
            // given
            val voteRequest = VoteRequest(1, 2, 5.0, 5.0)
            every { ratingRepository.findByWebtoonIdAndAccountId(voteRequest.webtoonId, voteRequest.accountId) }
                .returns(
                    Rating(
                        1,
                        webtoonFixture(id = voteRequest.webtoonId),
                        accountFixture(id = voteRequest.accountId),
                        1.0,
                        1.0,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                    )
                )

            // when
            val originScore = ratingService.upsertWebtoonScore(voteRequest)

            // then
            originScore.isUpdate.shouldBe(true)
            originScore.originStoryScore.shouldNotBe(voteRequest.storyScore)
            originScore.originDrawingScore.shouldNotBe(voteRequest.drawingScore)
        }
    }
})
