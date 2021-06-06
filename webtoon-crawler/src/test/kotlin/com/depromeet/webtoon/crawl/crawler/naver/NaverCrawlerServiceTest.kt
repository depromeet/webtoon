package com.depromeet.webtoon.crawl.crawler.naver

// internal class NaverCrawlerServiceTest : FunSpec({
//    lateinit var naverCrawlerService: NaverCrawlerService
//    lateinit var naverCrawlerFetchAdapter: NaverCrawlerFetchAdapter
//    lateinit var webtoonImportService: WebtoonImportService
//
//    beforeTest {
//        webtoonImportService = mockk()
//        naverCrawlerFetchAdapter = mockk()
//        naverCrawlerService = NaverCrawlerService(webtoonImportService, naverCrawlerFetchAdapter)
//    }
//
//    test("import 호출 확인") {
//        // given
//        every { naverCrawlerFetchAdapter.crawl() } returns listOf(
//            WebtoonImportRequest(
//                "test",
//                "test.url",
//                "test.thumbnail",
//                emptyList(),
//                emptyList(),
//                WebtoonSite.NAVER,
//                emptyList(),
//                0.0,
//                1,
//                "",
//                BackgroundColor.NONE,
//                true
//            )
//        )
//        every { webtoonImportService.importWebtoon(any()) } returns Webtoon()
//
//        // when
//        naverCrawlerService.crawlAndUpsert()
//
//        // then
//        verify(atLeast = 1) {
//            webtoonImportService.importWebtoon(any())
//        }
//    }
// })
