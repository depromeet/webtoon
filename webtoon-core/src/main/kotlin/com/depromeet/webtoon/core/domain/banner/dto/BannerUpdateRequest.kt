package com.depromeet.webtoon.core.domain.banner.dto

import com.depromeet.webtoon.core.domain.banner.model.BannerInventory
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import java.time.LocalDateTime

data class BannerUpdateRequest(
    val bannerInventory: BannerInventory,
    val caption: String,
    val webtoon: Webtoon,
    val priority: Int,
    val displayBeginDateTime: LocalDateTime,
    val displayEndDateTime: LocalDateTime,
)
