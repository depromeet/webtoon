package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.type.WebtoonSite

data class WebtoonWeekDayResponse(
    val sites: List<Site> = Site.allSites(),
    val webtoons: List<WebtoonResponse>
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

    companion object {
        fun allSites() = WebtoonSite.ALL_SITES.map { it.toSite() }
    }
}

fun WebtoonSite.toSite(): Site {
    return Site(this, this.thumbnail)
}
