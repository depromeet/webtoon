package com.depromeet.webtoon.crawl.crawler.daum.dto.webtoondetail

data class DaumWebtoonDetailCrawlResult(
    val data: Data
)

data class Data(
    val webtoon: Webtoon
)

data class Webtoon(
    val title: String,
    val nickname: String,
    val cartoon: Cartoon,
    val thumbnailImage2: ThumbnailImage2,
    val webtoonWeeks: List<WebtoonWeeks>?,
    val averageScore: Double,
    val introduction: String
)

data class ThumbnailImage2(
    val url: String
)

data class Cartoon(
    val artists: List<Artist>,
    val genres: List<Genre>
)

data class Artist(
    val name: String
)

data class Genre(
    val name: String
)

data class WebtoonWeeks(
    val weekDay: String
)
