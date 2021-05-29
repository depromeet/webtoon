package com.depromeet.webtoon.core.domain.banner.dto

import com.depromeet.webtoon.core.domain.banner.model.BannerInventory
import java.time.LocalDateTime

data class SearchBannerRequest(
    val bannerInventory: BannerInventory,
    val baseDateTime: LocalDateTime,
)
