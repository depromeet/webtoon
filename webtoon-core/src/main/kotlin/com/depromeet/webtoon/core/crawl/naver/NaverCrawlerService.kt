package com.depromeet.webtoon.core.crawl.naver

import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.core.crawl.daum.DaumCrawlerService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NaverCrawlerService(
    val webtoonImportService: WebtoonImportService,
    val naverCrawlerFetchAdapter: NaverCrawlerFetchAdapter
) {
    fun crawlAndUpsert() {
        val naverWebtoons = naverCrawlerFetchAdapter.crawl()
        // todo 개선사항: 한번에 Insert 하되 중복문제 해결 할 것
        naverWebtoons.map { webtoonImportService.importWebtoon(it) }
        //webtoonImportService.importWebtoons(naverWebtoons)
    }

    companion object {
        val log = LoggerFactory.getLogger(DaumCrawlerService::class.java)
    }
}
