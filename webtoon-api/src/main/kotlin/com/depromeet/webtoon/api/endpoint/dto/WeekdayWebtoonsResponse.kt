package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.type.WebtoonSite

data class WeekdayWebtoonsResponse(
    val sites: List<Site>,
    val webtoons: List<WeekdayWebtoon>
)

data class Site(
    val site: WebtoonSite,
    val thumbnail: String = site.thumbnail
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Site

        if (site != other.site) return false

        return true
    }

    override fun hashCode(): Int {
        return site.hashCode()
    }
}

fun WebtoonSite.toSite(): Site {
    return Site(this, this.thumbnail)
}
