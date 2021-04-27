package com.depromeet.webtoon.core.crawl.naver

import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

internal class NaverCrawlerServiceTest : FunSpec({
    lateinit var naverCrawlerService: NaverCrawlerService
    lateinit var webtoonImportService: WebtoonImportService

    beforeTest {
        webtoonImportService = mockk()
        naverCrawlerService = NaverCrawlerService(webtoonImportService)
    }

    test("import 호출 확인") {
        // given
        every { webtoonImportService.importWebtoon(any()) } returns webtoonFixture()

        // when
        naverCrawlerService.crawl()

        // then
        verify(atLeast = 1) {
            webtoonImportService.importWebtoon(any())
        }
    }
})
