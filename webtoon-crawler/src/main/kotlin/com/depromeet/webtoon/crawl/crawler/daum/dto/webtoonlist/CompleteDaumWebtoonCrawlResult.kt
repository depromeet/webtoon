package com.depromeet.webtoon.crawl.crawler.daum.dto.webtoonlist

data class CompleteDaumWebtoonCrawlResult(

    val data: List<CompleteData>

)

data class CompleteData(
    val nickname: String,
    val ageGrade: Int
)
