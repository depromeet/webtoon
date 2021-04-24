package com.depromeet.webtoon.core.crawl.daum

import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DaumCrawlerServiceTest : FunSpec({

    lateinit var webtoonImportService: WebtoonImportService

    beforeTest {
        webtoonImportService = mockk()
    }

    context("DaumCrawlerService") {
        test("updateDaumWebtoons 연관 메소드 호출 테스트") {
            // given
            val crawlService = DaumCrawlerService(webtoonImportService)
            val crawledSize = crawlService.getSortedNicknamesByPopularity().size

            every { webtoonImportService.importWebtoon(any()) } returns Webtoon()

            // when
            crawlService.updateDaumWebtoons()

            // then
            verify(exactly = crawledSize) { webtoonImportService.importWebtoon(any()) }
        }
    }
})
