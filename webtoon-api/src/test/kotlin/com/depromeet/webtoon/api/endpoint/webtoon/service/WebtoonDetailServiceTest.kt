package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime

class WebtoonDetailServiceTest : FunSpec({

    lateinit var webtoonDetailService: WebtoonDetailService
    lateinit var webtoonRepository: WebtoonRepository
    lateinit var reviewRepository: ReviewRepository



    beforeTest {
        webtoonRepository = mockk()
        reviewRepository = mockk()
        webtoonDetailService = WebtoonDetailService(webtoonRepository, reviewRepository)
    }

    context("WebtoonDetailService"){
        test("getWebtoonDetail"){

            // given
            val webtoon = Webtoon(
                1L,
                "테스트웹툰",
                WebtoonSite.DAUM,
                "test.url",
                listOf(Author(1L, "테스트작가", emptyList(), LocalDateTime.now(), LocalDateTime.now())),
                listOf(WeekDay.MON, WeekDay.TUE),
                1,
                "thumbnail.com",
                "테스트 줄거리",
                LocalDateTime.now(),
                LocalDateTime.now()
            )

            val title = "testTitle"
            val site = WebtoonSite.DAUM

            every { webtoonRepository.findBySiteAndTitle(any(), any()) } returns webtoon

            every { reviewRepository.getAvgDrawingScore(any()) } returns 3.0
            every { reviewRepository.getAvgStoryScore(any()) } returns 3.5
            every { reviewRepository.getComments(any()) } returns mutableListOf("재밌다", "재미없다")


            // when
            webtoonDetailService.getWebtoonDetail(title = title, site = site)

            // then
            verify(exactly = 1) {
                webtoonRepository.findBySiteAndTitle(title = title, site = site)
            }
            verify(exactly = 1){
                reviewRepository.getAvgStoryScore(
                    withArg {
                        it shouldBe webtoon
                    }
                )
            }
            verify(exactly = 1) {
                reviewRepository.getAvgDrawingScore(
                    withArg {
                        it shouldBe webtoon
                    }
                )
            }

            verify(exactly = 1){
                reviewRepository.getComments(
                    withArg {
                        it shouldBe webtoon
                    }
                )
            }

        }
    }


})
