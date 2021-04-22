package com.depromeet.webtoon.core.crawl.daum

import io.kotest.core.spec.style.FunSpec
import io.mockk.mockk

class DaumCrawlerServiceTest : FunSpec({

    context("DaumCrawlerService") {
        test("updateDaumWebtoons 연관 메소드 호출 테스트") {

            val crawlService = DaumCrawlerService(mockk())

            // when
            crawlService.updateDaumWebtoons()
        }
    }
})
