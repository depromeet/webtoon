package com.depromeet.webtoon.crawl.crawler.daum.dto.webtoonlist

data class LatestWebtoonEpisode(

    val id: Int,
    val episode: Int,
    val title: String,
    val thumbnailImage: ThumbnailImage,
    val episodeImage: EpisodeImage?,
    val dateCreated: Long
)
