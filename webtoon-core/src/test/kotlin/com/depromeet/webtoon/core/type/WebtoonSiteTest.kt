package com.depromeet.webtoon.core.type

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe

internal class WebtoonSiteTest : FunSpec({

    test("webtoon all_site value check") {
        // when
        val allSites = WebtoonSite.ALL_SITES

        // then
        // then:: 전체사이트는 NONE을 포함하지 않는다.
        allSites shouldNotContain WebtoonSite.NONE
        // then:: NONE을 포함하지 않으므로 사이즈는 전체보다 하나 작다.
        allSites.size shouldBe WebtoonSite.values().size - 1
    }
})
