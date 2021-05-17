package com.depromeet.webtoon.core.crawl.naver

import io.kotest.core.spec.style.FunSpec

internal class NaverCrawlerFetchAdapterTest : FunSpec({
    xtest("crawl naver") {
        val crawl = NaverCrawlerFetchAdapter().crawl()
        println(crawl)
    }
})
