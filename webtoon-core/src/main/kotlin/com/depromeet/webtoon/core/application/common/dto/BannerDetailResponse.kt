package com.depromeet.webtoon.core.application.common.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponse
import com.depromeet.webtoon.core.domain.banner.model.Banner
import com.depromeet.webtoon.core.domain.banner.model.BannerInventory
import java.time.LocalDateTime

data class BannerDetailResponse(
    val id: Long,
    val bannerInventory: BannerInventory,
    val caption: String,
    val webtoon: WebtoonResponse,
    val priority: Int,
    val displayBeginDateTime: LocalDateTime,
    val displayEndDateTime: LocalDateTime,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {
    companion object {
        fun List<Banner>.convertToBannerDetailResponses(): List<BannerDetailResponse> {
            return this.map { it.convertToBannerDetailResponse() }
        }

        fun Banner.convertToBannerDetailResponse(): BannerDetailResponse {
            return BannerDetailResponse(
                id = id!!,
                bannerInventory = bannerInventory,
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
