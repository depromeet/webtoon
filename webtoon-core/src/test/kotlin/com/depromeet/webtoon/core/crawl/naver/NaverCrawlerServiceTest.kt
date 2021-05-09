package com.depromeet.webtoon.core.crawl.naver

import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

internal class NaverCrawlerServiceTest : FunSpec({
    lateinit var naverCrawlerService: NaverCrawlerService
    lateinit var naverCrawlerFetchAdapter: NaverCrawlerFetchAdapter
    lateinit var webtoonImportService: WebtoonImportService

    beforeTest {
        webtoonImportService = mockk()
        naverCrawlerFetchAdapter = mockk()
        naverCrawlerService = NaverCrawlerService(webtoonImportService, naverCrawlerFetchAdapter)
    }

    test("import 호출 확인") {
        // given
        every { naverCrawlerFetchAdapter.crawl() } returns emptyList()
        every { webtoonImportService.importWebtoons(any()) } returns emptyList()

        // when
        naverCrawlerService.crawlAndUpsert()

        // then
        verify(atLeast = 1) {
            webtoonImportService.importWebtoons(any())
        }
    }
})
