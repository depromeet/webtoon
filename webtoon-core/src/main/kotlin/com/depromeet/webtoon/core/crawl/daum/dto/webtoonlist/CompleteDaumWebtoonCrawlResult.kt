package com.depromeet.webtoon.core.crawl.daum.dto.webtoonlist

data class CompleteDaumWebtoonCrawlResult(

    val data: List<CompleteData>

)
data class CompleteData(
    val nickname: String,
    val ageGrade: Int
)
