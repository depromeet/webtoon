package com.depromeet.webtoon.api.endpoint.webtoon

import com.depromeet.webtoon.api.common.annotation.SwaggerAuthApi
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.api.endpoint.webtoon.service.WebtoonDetailService
import io.swagger.annotations.Api
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "WebtoonDetailController")
class WebtoonDetailController(
    val webtoonDetailService: WebtoonDetailService,
) {

    @GetMapping("/api/v1/webtoons/detail")
    @SwaggerAuthApi
    fun getWebtoonDetail(
        @RequestParam id: Long,
    ): ApiResponse<WebtoonDetailResponse> {
        log.info("[WebtoonDetailController] getWebtoonDetail( webtoonId : $id )")
        return webtoonDetailService.getWebtoonDetail(id)
    }

    companion object {
        private val log = LoggerFactory.getLogger(WebtoonDetailController::class.java)
    }
}
