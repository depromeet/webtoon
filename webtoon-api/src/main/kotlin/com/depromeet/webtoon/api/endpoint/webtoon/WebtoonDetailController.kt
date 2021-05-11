package com.depromeet.webtoon.api.endpoint.webtoon

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.api.endpoint.webtoon.service.WebtoonDetailService
import com.depromeet.webtoon.core.type.WebtoonSite
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Api("WebtoonDetailController")
class WebtoonDetailController(
    val webtoonDetailService: WebtoonDetailService,
) {
    private val log = LoggerFactory.getLogger(WebtoonController::class.java)

    @GetMapping("/api/v1/webtoons/detail")
    @ApiImplicitParams(
        ApiImplicitParam(name = "title", value = "웹툰 제목", required = true),
        ApiImplicitParam(name = "site", value = "웹툰 플랫폼", required = true)
    )
    fun getWebtoonDetail(
        @RequestParam title: String,
        @RequestParam site: WebtoonSite
    ): ApiResponse<WebtoonDetailResponse> {

        val webtoonDetail = webtoonDetailService.getWebtoonDetail(site, title)

        return webtoonDetail
    }
}
