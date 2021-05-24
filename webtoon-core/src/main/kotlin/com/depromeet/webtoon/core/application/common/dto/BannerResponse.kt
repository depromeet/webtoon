package com.depromeet.webtoon.core.application.common.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponse
import com.depromeet.webtoon.core.domain.banner.model.Banner

data class BannerResponse(
    val id: Long,
    val caption: String,
    val webtoon: WebtoonResponse,
) {
    companion object {
        fun List<Banner>.convertToBannerResponses(): List<BannerResponse> {
            return this.map { it.convertToBannerResponse() }
        }

        fun Banner.convertToBannerResponse(): BannerResponse {
            return BannerResponse(
                id = id!!,
                caption = caption,
                webtoon = webtoon.convertToWebtoonResponse(),
            )
        }
    }
}
