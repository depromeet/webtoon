package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.type.WebtoonSite

data class WeekdayWebtoonsResponse(
    val sites: List<Site>,
    val webtoons: List<Webtoon>
)

data class Site(
    val site: WebtoonSite,
    val thumbnail: String
)
