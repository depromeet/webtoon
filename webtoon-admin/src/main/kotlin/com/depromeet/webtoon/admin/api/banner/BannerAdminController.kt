package com.depromeet.webtoon.admin.api.banner

import com.depromeet.webtoon.core.application.admin.banner.BannerAdminService
import com.depromeet.webtoon.core.application.admin.common.AdminListFetchResult
import com.depromeet.webtoon.core.application.common.dto.BannerDetailResponse
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/banners")
class BannerAdminController(
    val bannerAdminService: BannerAdminService
) {
    @Transactional(readOnly = true)
    @GetMapping("")
    fun webtoonList(
        @RequestParam(required = false, defaultValue = "0") page: Int?,
        @RequestParam(required = false, defaultValue = "25") pageSize: Int?,
        httpServletResponse: HttpServletResponse
    ): AdminListFetchResult<BannerDetailResponse> {
        return bannerAdminService.fetchBanners(page!!, pageSize!!)
    }
}
