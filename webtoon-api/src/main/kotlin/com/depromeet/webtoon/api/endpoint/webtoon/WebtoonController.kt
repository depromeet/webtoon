package com.depromeet.webtoon.api.endpoint.webtoon

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse.Companion.ok
import com.depromeet.webtoon.api.endpoint.dto.WeekdayWebtoonsResponse
import com.depromeet.webtoon.api.endpoint.dto.convertToWeekDayWebtoons
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
import com.depromeet.webtoon.core.type.WeekDay
import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Api("WebtoonController")
class WebtoonController(
    val webtoonService: WebtoonService
) {
    private val log = LoggerFactory.getLogger(WebtoonController::class.java)

    @GetMapping("/api/v1/webtoons/weekday/{weekDay}")
    fun getWebtoonRealList(
        @ApiParam("요일", required = true, example = "mon")
        @PathVariable(name = "weekDay")
        weekDayStr: String
    ): ApiResponse<WeekdayWebtoonsResponse> {
        val weekDay = WeekDay.parse(weekDayStr)
        log.info("[WebtoonController] - GET /api/v1/webtoons/$weekDay 요청")

        return webtoonService.getWeekdayWebtoons(weekDay)
            .let { ok(WeekdayWebtoonsResponse(webtoons = it.convertToWeekDayWebtoons())) }
    }
}
