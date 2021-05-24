package com.depromeet.webtoon.core.application.common.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponse
import com.depromeet.webtoon.core.domain.banner.model.Banner
import com.depromeet.webtoon.core.domain.banner.model.BannerType
import java.time.LocalDateTime

data class BannerDetailResponse(
    val id: Long,
    val bannerType: BannerType,
    val caption: String,
    val webtoon: WebtoonResponse,
    val priority: Int,
    val displayBeginDateTime: LocalDateTime,
    val displayEndDateTime: LocalDateTime,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {
    companion object {
        fun List<Banner>.convertToBannerAdminResponses(): List<BannerDetailResponse> {
            return this.map { it.convertToBannerAdminResponse() }
        }

        fun Banner.convertToBannerAdminResponse(): BannerDetailResponse {
            return BannerDetailResponse(
                id = id!!,
                bannerType = bannerType,
                caption = caption,
                webtoon = webtoon.convertToWebtoonResponse(),
                priority = priority,
                displayBeginDateTime = displayBeginDateTime,
                displayEndDateTime = displayEndDateTime,
                createdAt = createdAt!!,
                modifiedAt = modifiedAt!!
            )
        }
    }
}
