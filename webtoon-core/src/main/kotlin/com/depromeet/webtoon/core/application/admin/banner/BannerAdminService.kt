package com.depromeet.webtoon.core.application.admin.banner

import com.depromeet.webtoon.core.application.admin.banner.dto.BannerCreateAdminRequest
import com.depromeet.webtoon.core.application.admin.common.AdminListFetchResult
import com.depromeet.webtoon.core.application.common.dto.BannerDetailResponse
import com.depromeet.webtoon.core.application.common.dto.BannerDetailResponse.Companion.convertToBannerAdminResponse
import com.depromeet.webtoon.core.application.common.dto.BannerDetailResponse.Companion.convertToBannerAdminResponses
import com.depromeet.webtoon.core.domain.banner.dto.BannerCreateRequest
import com.depromeet.webtoon.core.domain.banner.service.BannerService
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
import org.springframework.stereotype.Service

@Service
class BannerAdminService(
    val bannerService: BannerService,
    val webtoonService: WebtoonService
) {
    fun createBanner(
        request: BannerCreateAdminRequest
    ): BannerDetailResponse {

        val bannerCreateRequest = BannerCreateRequest(
            bannerType = request.bannerType,
            caption = request.caption,
            webtoon = webtoonService.findById(request.webtoonId),
            priority = request.priority,
            displayBeginDateTime = request.displayBeginDateTime,
            displayEndDateTime = request.displayEndDateTime
        )

        return bannerService
            .createBanner(bannerCreateRequest)
            .convertToBannerAdminResponse()
    }

    fun fetchBanners(page: Int, pageSize: Int): AdminListFetchResult<BannerDetailResponse> {
        val bannerRepository = bannerService.bannerRepository
        val result = bannerRepository.fetchForAdmin(page, pageSize).convertToBannerAdminResponses()
        val total = bannerRepository.fetchCountForAdmin()

        return AdminListFetchResult(result, total)
    }
}
