package com.depromeet.webtoon.crawl.crawler.naver

import io.kotest.core.spec.style.FunSpec

class NaverWebtoonCrawlerTest : FunSpec({

    test("crawlOngoingWebtoons") {
        val crawlOngoingWebtoons = NaverWebtoonCrawler().crawlOngoingWebtoons()

        println(crawlOngoingWebtoons)
    }
})
