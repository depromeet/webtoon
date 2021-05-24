package com.depromeet.webtoon.core.domain.banner.service

import com.depromeet.webtoon.core.domain.banner.dto.BannerCreateRequest
import com.depromeet.webtoon.core.domain.banner.model.BannerType
import com.depromeet.webtoon.core.domain.banner.repository.BannerRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

class BannerServiceTest : FunSpec({
    lateinit var bannerService: BannerService
    lateinit var bannerRepository: BannerRepository

    beforeTest {
        bannerRepository = mockk(relaxed = true)
        bannerService = BannerService(bannerRepository)
    }

    context("배너 생성 테스트") {
        test("banner create test") {
            // given
            val baseDateTime = LocalDateTime.now()
            val createRequest = BannerCreateRequest(
                bannerType = BannerType.HOME_MAIN,
                caption = "테스트 캡숀",
                webtoon = webtoonFixture(id = 1L),
                priority = 1,
                displayBeginDateTime = baseDateTime.minusDays(3),
                displayEndDateTime = baseDateTime.plusDays(3)
            )

            every { bannerRepository.save(any()) } answers { firstArg() }

            // when
            val createdBanner = bannerService.createBanner(createRequest)

            // then
            with(createdBanner) {
                bannerType shouldBe createRequest.bannerType
                caption shouldBe createRequest.caption
                webtoon shouldBe createRequest.webtoon
                priority shouldBe createRequest.priority
                displayBeginDateTime shouldBe createRequest.displayBeginDateTime
                displayEndDateTime shouldBe createRequest.displayEndDateTime
            }
        }
    }
})
