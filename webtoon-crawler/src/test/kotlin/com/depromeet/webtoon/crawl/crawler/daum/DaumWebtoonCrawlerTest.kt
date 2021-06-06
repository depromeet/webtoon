package com.depromeet.webtoon.crawl.crawler.daum

import com.depromeet.webtoon.common.dto.imports.WebtoonImportRequest
import com.depromeet.webtoon.common.type.BackgroundColor
import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.crawl.crawler.daum.dto.webtoondetail.Cartoon
import com.depromeet.webtoon.crawl.crawler.daum.dto.webtoondetail.Data
import com.depromeet.webtoon.crawl.crawler.daum.dto.webtoondetail.DaumWebtoonDetailCrawlResult
import com.depromeet.webtoon.crawl.crawler.daum.dto.webtoondetail.ThumbnailImage2
import com.depromeet.webtoon.crawl.crawler.daum.dto.webtoondetail.Webtoon
import com.depromeet.webtoon.crawl.crawler.daum.dto.webtoondetail.WebtoonWeeks
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DaumWebtoonCrawlerTest : FunSpec({
    lateinit var daumWebtoonCrawler: DaumWebtoonCrawler
    lateinit var fetchService: DaumWebtoonFetchClient

    beforeTest {
        fetchService = mockk()
        daumWebtoonCrawler = DaumWebtoonCrawler(fetchService)
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

        // when
        val completeWebtoons = daumWebtoonCrawler.crawlCompletedWebtoons()

        // then
        verify(exactly = 1) { fetchService.crawlCompletedWebtoonNicknames() }
        verify(exactly = 1) { fetchService.crawlCompletedWebtoonDetail(detailResult.data.webtoon.title) }
    }

    test("[DaumCrawlerService] - crawlSortedWebtoonNicknames()") {
        // given
        every { fetchService.getPopularNicknames() } returns arrayListOf("nick", "notToday")
        every { fetchService.getTodayUpdatedNicknames() } returns arrayListOf("nick", "nick2")

        // when
        val resultNicknames = daumWebtoonCrawler.crawlSortedWebtoonNicknames()

        // then
        verify(exactly = 1) { fetchService.getPopularNicknames() }
        verify(exactly = 1) { fetchService.getTodayUpdatedNicknames() }

        resultNicknames shouldHaveSize 2
        resultNicknames shouldContainExactly listOf("nick", "nick2")
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
        every { daumWebtoonCrawler.crawlSortedWebtoonNicknames() } returns listOf("nick")
        every { fetchService.crawlWebtoonDetail(any()) } returns detailResult

        // when
        val ongoingWebtoons = daumWebtoonCrawler.crawlOngoingWebtoons()

        // then
        verify(exactly = 1) { daumWebtoonCrawler.crawlSortedWebtoonNicknames() }
        verify(exactly = 1) { fetchService.crawlWebtoonDetail("nick") }
    }
})
