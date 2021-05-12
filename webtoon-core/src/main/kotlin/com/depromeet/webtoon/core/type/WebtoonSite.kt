package com.depromeet.webtoon.core.type

enum class WebtoonSite(val thumbnail: String) {
    NAVER("http://testNaverThumbnail.test"),
    DAUM("http://testDaumThumbnail.test"),
    NONE(""), ;

    companion object {
        val ALL_SITES = WebtoonSite.values().filter { it != NONE }
    }
}
