package com.depromeet.webtoon.admin.api.banner

import com.depromeet.webtoon.core.application.admin.banner.BannerAdminService
import com.depromeet.webtoon.core.application.admin.banner.dto.BannerCreateAdminRequest
import com.depromeet.webtoon.core.application.admin.banner.dto.BannerUpdateAdminRequest
import com.depromeet.webtoon.core.application.admin.common.AdminListFetchResult
import com.depromeet.webtoon.core.application.common.dto.BannerDetailResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/banners")
class BannerAdminController(
    val bannerAdminService: BannerAdminService
) {
    @GetMapping("")
    fun getBannerList(
        @RequestParam(required = false, defaultValue = "1") page: Int?,
        @RequestParam(required = false, defaultValue = "25") pageSize: Int?,
        httpServletResponse: HttpServletResponse
    ): AdminListFetchResult<BannerDetailResponse> {
        return bannerAdminService.fetchBanners(page!!, pageSize!!)
    }

    @GetMapping("/{bannerId}")
    fun getBannerOne(
        @PathVariable bannerId: Long
    ): BannerDetailResponse {
        return bannerAdminService.fetchBanner(bannerId)
    }

    @PostMapping("")
    fun createBanner(
        @RequestBody bannerCreateAdminRequest: BannerCreateAdminRequest,
    ): BannerDetailResponse {
        return bannerAdminService.createBanner(bannerCreateAdminRequest)
    }

    @PutMapping("/{bannerId}")
    fun updateBanner(
        @PathVariable bannerId: Long,
        @RequestBody bannerUpdateAdminRequest: BannerUpdateAdminRequest,
    ): BannerDetailResponse {
        return bannerAdminService.updateBanner(bannerId, bannerUpdateAdminRequest)
    }
}
