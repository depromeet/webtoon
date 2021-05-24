package com.depromeet.webtoon.core.application.admin.banner.dto

import com.depromeet.webtoon.core.domain.banner.model.BannerType
import java.time.LocalDateTime

data class BannerCreateAdminRequest(
    val bannerType: BannerType,
    val caption: String,
    val webtoonId: Long,
    val priority: Int,
    val displayBeginDateTime: LocalDateTime,
    val displayEndDateTime: LocalDateTime,
)
