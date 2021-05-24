package com.depromeet.webtoon.api.endpoint.webtoon

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.api.endpoint.webtoon.service.WebtoonDetailService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "WebtoonDetailController")
class WebtoonDetailController(
    val webtoonDetailService: WebtoonDetailService,
) {
    private val log = LoggerFactory.getLogger(WebtoonListController::class.java)

    @GetMapping("/api/v1/webtoons/detail")
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "웹툰 id", required = true)
    )
    fun getWebtoonDetail(
        @RequestParam id: Long,
    ): ApiResponse<WebtoonDetailResponse> {
        return webtoonDetailService.getWebtoonDetail(id)
    }
}
