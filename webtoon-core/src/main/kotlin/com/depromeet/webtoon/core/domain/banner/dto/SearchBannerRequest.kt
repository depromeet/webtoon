package com.depromeet.webtoon.core.domain.banner.dto

import com.depromeet.webtoon.core.domain.banner.model.BannerType
import java.time.LocalDateTime

data class SearchBannerRequest(
    val bannerType: BannerType,
    val baseDateTime: LocalDateTime,
)
