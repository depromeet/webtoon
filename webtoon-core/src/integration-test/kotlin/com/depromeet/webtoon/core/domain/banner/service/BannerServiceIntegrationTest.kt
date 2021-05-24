package com.depromeet.webtoon.core.domain.banner.service

import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.banner.bannerFixture
import com.depromeet.webtoon.core.domain.banner.dto.SearchBannerRequest
import com.depromeet.webtoon.core.domain.banner.model.BannerType
import com.depromeet.webtoon.core.domain.banner.model.BannerType.HOME_MAIN
import com.depromeet.webtoon.core.domain.banner.model.BannerType.NONE
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.testsupport.AuthorTestDataHelper.Companion.save
import com.depromeet.webtoon.core.testsupport.BannerTestDataHelper.Companion.saveAll
import com.depromeet.webtoon.core.testsupport.WebtoonTestDataHelper.Companion.save
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@SpringBootTest
class BannerServiceIntegrationTest(
    val bannerService: BannerService
) : FunSpec({

    context("배너 조회 테스트") {
        val baseDateTime = LocalDateTime.now()
        val testWebtoon = webtoonFixture(authors = listOf(authorFixture().save())).save()
        fun testBanner(
            bannerType: BannerType = HOME_MAIN,
            beginDateTime: LocalDateTime = baseDateTime.minusDays(1),
            endDateTime: LocalDateTime = baseDateTime.plusDays(1),
            priority: Int = 0,
        ) = bannerFixture(
            bannerType = bannerType,
            webtoon = testWebtoon,
            displayBeginDateTime = beginDateTime,
            displayEndDateTime = endDateTime,
            priority = priority
        )

        test("날짜와 타입이 매칭된 배너만 나오는지 확인한다.") {
            // given
            val testBanners = listOf(
                // 조건 일치
                testBanner(HOME_MAIN, baseDateTime.minusDays(1), baseDateTime.plusDays(1)),
                // 조건 일치
                testBanner(HOME_MAIN, baseDateTime.minusDays(2), baseDateTime.plusDays(2)),
                // 날짜 범위 포함안됨
                testBanner(HOME_MAIN, baseDateTime.plusDays(2), baseDateTime.plusDays(4)),
                // 날짜 범위 포함안됨
                testBanner(HOME_MAIN, baseDateTime.minusDays(4), baseDateTime.minusDays(2)),
                // 배너 유형 다름
                testBanner(NONE, baseDateTime.minusDays(1), baseDateTime.plusDays(1)),
            ).saveAll()

            // when
            val searchedBanners = bannerService.searchBanners(SearchBannerRequest(HOME_MAIN, baseDateTime))

            // then
            searchedBanners shouldHaveSize 2
            searchedBanners shouldContainAll listOf(testBanners[0], testBanners[1])
        }

        test("일치하는 경우 priority asc / id desc 으로 정렬되는지 확인한다.") {
            // given
            val testBanners = listOf(
                // 4번째
                testBanner(priority = 3),
                // 2번째
                testBanner(priority = 2),
                // 3번째
                testBanner(priority = 3),
                // 1번째
                testBanner(priority = 1),
            ).saveAll()

            // when
            val searchedBanners = bannerService.searchBanners(SearchBannerRequest(HOME_MAIN, baseDateTime))

            // then
            searchedBanners shouldHaveSize 4
            searchedBanners shouldContainInOrder listOf(testBanners[3], testBanners[1], testBanners[2], testBanners[0])
        }
    }
})
