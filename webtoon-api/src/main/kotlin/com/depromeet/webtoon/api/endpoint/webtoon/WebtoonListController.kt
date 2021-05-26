package com.depromeet.webtoon.api.endpoint.webtoon

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse.Companion.ok
import com.depromeet.webtoon.api.endpoint.dto.WebtoonSearchResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonWeekDayResponse
import com.depromeet.webtoon.api.endpoint.webtoon.dto.AuthorWebtoonResponse
import com.depromeet.webtoon.api.endpoint.webtoon.service.AuthorWebtoonService
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonSearchService
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
import com.depromeet.webtoon.core.type.WeekDay
import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api/v1/webtoons")
@Api("WebtoonListController")
class WebtoonListController(
    val webtoonService: WebtoonService,
    val authorWebtoonService: AuthorWebtoonService,
    val webtoonSearchService: WebtoonSearchService,
) {
    private val log = LoggerFactory.getLogger(WebtoonListController::class.java)

    @GetMapping("/author/{authorName}")
    fun findAuthorWebtoons(
        @ApiParam("작가이름", required = true, example = "조석")
        @PathVariable(name = "authorName")
        authorName: String
    ): ApiResponse<AuthorWebtoonResponse> {
        log.info("[WebtoonListController] 작가별 작품 조회 작가명 $authorName")
        return authorWebtoonService.getAuthorWebtoons(authorName)
    }

    @GetMapping("/weekday/{weekDay}")
    fun findWeekDayWebtoons(
        @ApiParam("요일", required = true, example = "mon")
        @PathVariable(name = "weekDay")
        weekDayStr: String
    ): ApiResponse<WebtoonWeekDayResponse> {
        val weekDay = WeekDay.parse(weekDayStr)
        log.info("[WebtoonListController] weekDay 웹툰 리스트 조회 weekDay : $weekDay")

        val webtoons = webtoonService.getWeekdayWebtoons(weekDay)
        return ok(WebtoonWeekDayResponse(webtoons = webtoons.convertToWebtoonResponses()))
    }

    @GetMapping("/search")
    fun searchWebtoons(
        @ApiParam("검색어", required = true, example = "조석")
        @RequestParam("query")
        queryStr: String
    ): ApiResponse<WebtoonSearchResponse> {
        log.info("[WebtoonListController] 조회쿼리 기반 웹툰 리스트 조회 queryStr : $queryStr")

        val webtoons = webtoonSearchService.searchByQueryStr(queryStr)
        return ok(WebtoonSearchResponse(queryStr, webtoons.convertToWebtoonResponses()))
    }
}
