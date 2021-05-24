package com.depromeet.webtoon.core.domain.banner

import com.depromeet.webtoon.core.domain.banner.model.Banner
import com.depromeet.webtoon.core.domain.banner.model.BannerType
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import java.time.LocalDateTime

fun bannerFixture(
    id: Long? = null,
    bannerType: BannerType = BannerType.HOME_MAIN,
    caption: String = "테스트 캡션",
    webtoon: Webtoon = webtoonFixture(),
    priority: Int = 0,
    displayBeginDateTime: LocalDateTime = LocalDateTime.now().minusYears(1),
    displayEndDateTime: LocalDateTime = LocalDateTime.now().plusYears(1)
) = Banner(
    id = id,
    bannerType = bannerType,
    caption = caption,
    webtoon = webtoon,
    priority = priority,
    displayBeginDateTime = displayBeginDateTime,
    displayEndDateTime = displayEndDateTime
)
