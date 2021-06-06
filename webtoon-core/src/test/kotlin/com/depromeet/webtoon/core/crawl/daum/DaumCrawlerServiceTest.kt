package com.depromeet.webtoon.core.crawl.daum

import com.depromeet.webtoon.common.dto.imports.WebtoonImportRequest
import com.depromeet.webtoon.common.type.BackgroundColor
import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.core.crawl.daum.dto.webtoondetail.Cartoon
import com.depromeet.webtoon.core.crawl.daum.dto.webtoondetail.Data
import com.depromeet.webtoon.core.crawl.daum.dto.webtoondetail.DaumWebtoonDetailCrawlResult
import com.depromeet.webtoon.core.crawl.daum.dto.webtoondetail.ThumbnailImage2
import com.depromeet.webtoon.core.crawl.daum.dto.webtoondetail.Webtoon
import com.depromeet.webtoon.core.crawl.daum.dto.webtoondetail.WebtoonWeeks
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DaumCrawlerServiceTest : FunSpec({

    lateinit var daumCrawlerService: DaumCrawlerService
    lateinit var webtoonImportService: WebtoonImportService
    lateinit var fetchService: DaumCrawlerFetchService

    beforeTest {
        webtoonImportService = mockk()
        fetchService = mockk()
        daumCrawlerService = DaumCrawlerService(webtoonImportService, fetchService)
    }

    test("[DaumCrawlerService] - updateCompletedDaumWebtoons()") {
        // given
        val detailResult =
            DaumWebtoonDetailCrawlResult(
                Data(
                    Webtoon(
                        "testTitle", "nick",
                        Cartoon(
                            emptyList(), emptyList()
                        ),
                        ThumbnailImage2("thumbnail"), listOf(WebtoonWeeks("mon")), 5.0, "줄거리"
                    )
                )
            )
        val webtoonImportRequest =
            WebtoonImportRequest(
                title = detailResult.data.webtoon.title,
                url = "http://webtoon.daum.net/webtoon/view/${detailResult.data.webtoon.nickname}",
                thumbnailImage = detailResult.data.webtoon.thumbnailImage2.url,
                weekdays = listOf(WeekDay.NONE),
                authors = emptyList(),
                site = WebtoonSite.DAUM,
                genres = emptyList(),
                score = detailResult.data.webtoon.averageScore,
                summary = detailResult.data.webtoon.introduction,
                popular = 0,
                backgroundColor = BackgroundColor.NONE,
                isComplete = true
            )
        every { fetchService.crawlCompletedWebtoonNicknames() } returns listOf(detailResult.data.webtoon.title)
        every { fetchService.crawlCompletedWebtoonDetail(any()) } returns detailResult
        every { webtoonImportService.importWebtoon(any()) } returns webtoonFixture()

        // when
        daumCrawlerService.updateCompletedDaumWebtoons()

        // then
        verify(exactly = 1) { fetchService.crawlCompletedWebtoonNicknames() }
        verify(exactly = 1) { fetchService.crawlCompletedWebtoonDetail(detailResult.data.webtoon.title) }
        verify(exactly = 1) { webtoonImportService.importWebtoon(webtoonImportRequest) }
    }

    test("[DaumCrawlerService] - crawlSortedWebtoonNicknames()") {
        // given
        every { fetchService.getPopularNicknames() } returns arrayListOf("nick", "notToday")
        every { fetchService.getTodayUpdatedNicknames() } returns arrayListOf("nick", "nick2")

        // when
        val result = daumCrawlerService.crawlSortedWebtoonNicknames()

        // then
        verify(exactly = 1) { fetchService.getPopularNicknames() }
        verify(exactly = 1) { fetchService.getTodayUpdatedNicknames() }

        result.size.shouldBe(2)
        result[0].shouldBe("nick")
        result[1].shouldBe("nick2")
    }

    test("[DaumCrawlerService] - updateDaumWebtoons()") {
        // given

        val detailResult =
            DaumWebtoonDetailCrawlResult(
                Data(
                    Webtoon(
                        "testTitle", "nick",
                        Cartoon(
                            emptyList(), emptyList()
                        ),
                        ThumbnailImage2("thumbnail"), listOf(WebtoonWeeks("mon")), 5.0, "줄거리"
                    )
                )
            )

        val webtoonImportRequest = WebtoonImportRequest(
            "testTitle",
            "http://webtoon.daum.net/webtoon/view/nick",
            "thumbnail",
            listOf(WeekDay.MON),
            emptyList(),
            WebtoonSite.DAUM,
            emptyList(),
            5.0,
            1,
            "줄거리",
            BackgroundColor.NONE,
            false,
        )
        every { fetchService.getPopularNicknames() } returns arrayListOf("nick")
        every { fetchService.getTodayUpdatedNicknames() } returns arrayListOf("nick")
        every { daumCrawlerService.crawlSortedWebtoonNicknames() } returns listOf("nick")
        every { webtoonImportService.importWebtoon(webtoonImportRequest) } returns webtoonFixture()
        every { fetchService.crawlWebtoonDetail(any()) } returns detailResult

        // when
        daumCrawlerService.updateDaumWebtoons()

        // then
        verify(exactly = 1) { daumCrawlerService.crawlSortedWebtoonNicknames() }
        verify(exactly = 1) { fetchService.crawlWebtoonDetail("nick") }
        verify(exactly = 1) { webtoonImportService.importWebtoon(webtoonImportRequest) }
    }
})
