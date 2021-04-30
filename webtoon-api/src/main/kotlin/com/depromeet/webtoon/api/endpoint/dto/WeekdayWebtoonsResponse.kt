package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.type.WebtoonSite

data class WeekdayWebtoonsResponse(
    val sites: List<Site>,
    val webtoons: List<WeekdayWebtoon>
)

data class Site(
    val site: WebtoonSite,
    val thumbnail: String
)
