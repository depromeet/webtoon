package com.depromeet.webtoon.api.endpoint.webtoon

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.Site
import com.depromeet.webtoon.api.endpoint.dto.WeekdayWebtoon
import com.depromeet.webtoon.api.endpoint.dto.WeekdayWebtoonsResponse
import com.depromeet.webtoon.core.domain.webtoon.dto.WebtoonCreateRequest
import com.depromeet.webtoon.core.domain.webtoon.dto.WebtoonCreateResponseDto
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import com.depromeet.webtoon.core.type.WebtoonSite
import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.time.DayOfWeek

@RestController
@Api("WebtoonController")
class WebtoonController(
    val webtoonService: WebtoonService
) {

    private val log = LoggerFactory.getLogger(WebtoonController::class.java)

    @GetMapping("/api/v1/webtoons/{weekDay}")
    fun getWebtoonList(@ApiParam("요일", required = true, example = "mon") @PathVariable weekDay: DayOfWeek): ApiResponse<WeekdayWebtoonsResponse> {

        val webtoons = webtoonService.getWeekdayWebtoons(weekDay.name)

        return if (webtoons != null) {
            ApiResponse(
                HttpStatus.OK, null,
                WeekdayWebtoonsResponse(
                    listOf(Site(WebtoonSite.DAUM, "daum.test"), Site(WebtoonSite.NAVER, "naver.test")),
                    webtoons.map { WeekdayWebtoon(it.id!!, it.site.name, it.title, it.authors, it.popularity, it.thumbnail!!) }
                )
            )
        } else {
            ApiResponse(
                HttpStatus.OK,
                "해당요일의 웹툰이 존재하지 않습니다",
                null
            )
        }
    }

    @PostMapping("/api/v1/webtoons")
    @ResponseBody
    fun createWebtoon(
        @RequestBody @Validated webtoonCreateRequest: WebtoonCreateRequest,
        errors: Errors
    ): ResponseEntity<WebtoonCreateResponseDto> {
        if (errors.hasErrors()) {
            val defaultMessage = errors.fieldError?.defaultMessage
            throw ApiValidationException(defaultMessage!!)
        }
        log.info("웹툰 생성 요청 입력 값 name -> ${webtoonCreateRequest.title}")
        val createdWebtoonDto = webtoonService.createWebtoon(webtoonCreateRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWebtoonDto)
    }
}
