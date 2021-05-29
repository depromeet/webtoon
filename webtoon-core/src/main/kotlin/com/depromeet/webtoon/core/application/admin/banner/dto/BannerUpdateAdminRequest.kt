package com.depromeet.webtoon.core.application.admin.banner.dto

import com.depromeet.webtoon.core.domain.banner.model.BannerInventory
import java.time.LocalDateTime

data class BannerUpdateAdminRequest(
    val bannerInventory: BannerInventory,
    val caption: String,
    val webtoonId: Long,
    val priority: Int,
    val displayBeginDateTime: LocalDateTime,
    val displayEndDateTime: LocalDateTime,
)
