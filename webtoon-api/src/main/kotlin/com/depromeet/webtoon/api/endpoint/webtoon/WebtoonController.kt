 package com.depromeet.webtoon.api.endpoint.webtoon

 import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
 import com.depromeet.webtoon.api.endpoint.dto.Site
 import com.depromeet.webtoon.api.endpoint.dto.WeekdayWebtoon
 import com.depromeet.webtoon.api.endpoint.dto.WeekdayWebtoonsResponse
 import com.depromeet.webtoon.api.endpoint.dto.convertToWeekDayWebtoons
 import com.depromeet.webtoon.api.endpoint.dto.toSite
 import com.depromeet.webtoon.core.domain.author.model.Author
 import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
 import com.depromeet.webtoon.core.type.WebtoonSite.DAUM
 import com.depromeet.webtoon.core.type.WebtoonSite.NAVER
 import com.depromeet.webtoon.core.type.WeekDay
 import io.swagger.annotations.Api
 import io.swagger.annotations.ApiParam
 import org.slf4j.LoggerFactory
 import org.springframework.web.bind.annotation.GetMapping
 import org.springframework.web.bind.annotation.PathVariable
 import org.springframework.web.bind.annotation.RestController
 import java.time.LocalDateTime

 @RestController
 @Api("WebtoonController")
 class WebtoonController(
    val webtoonService: WebtoonService
 ) {

    private val log = LoggerFactory.getLogger(WebtoonController::class.java)

    @GetMapping("/api/v1/webtoons/{weekDay}")
    fun getWebtoonList(
        @ApiParam(
            "요일",
            required = true,
            example = "MON"
        ) @PathVariable weekDay: WeekDay
    ): ApiResponse<WeekdayWebtoonsResponse> {

        log.info("[WebtoonController] - GET /api/v1/webtoons/$weekDay 요청")

        val dummyWebtoons = listOf(
            WeekdayWebtoon(
                1L, DAUM.name,
                "호랑이형님",
                listOf(
                    Author(1L, "이상규", emptyList(), LocalDateTime.now(), LocalDateTime.now()),
                    Author(3L, "홍길동", emptyList(), LocalDateTime.now(), LocalDateTime.now())
                ),
                1,
                "http://testThumbnail.com"
            ),

            WeekdayWebtoon(
                2L, NAVER.name,
                "프리드로우",
                listOf(
                    Author(2L, "전선욱", emptyList(), LocalDateTime.now(), LocalDateTime.now())
                ),
                2,
                "http://testThumbnail.com"
            )
        )
        return ApiResponse.ok(
            WeekdayWebtoonsResponse(
                listOf(Site(NAVER), Site(DAUM)),
                dummyWebtoons
            )
        )

        /* 실제 사용할 코드.
         val webtoons = webtoonService.getWeekdayWebtoons(weekDay)

        /return if (webtoons != null) {
            ApiResponse(
                HttpStatus.OK, null,
                WeekdayWebtoonsResponse(
                    listOf(Site(WebtoonSite.NAVER), Site(WebtoonSite.DAUM)),
                    webtoons.map { WeekdayWebtoon(it.id!!, it.site.name, it.title, it.authors, it.popularity, it.thumbnail) }
                )
            )
        } else {
            ApiResponse(
                HttpStatus.OK,
                "해당요일의 웹툰이 존재하지 않습니다",
                null
            )
        }*/
    }

    @GetMapping("/api/v1/webtoons_real/{weekDay}")
    fun getWebtoonRealList(
        @ApiParam(
            "요일",
            required = true,
            example = "MON"
        ) @PathVariable weekDay: WeekDay
    ): ApiResponse<WeekdayWebtoonsResponse> {

        log.info("[WebtoonController] - GET /api/v1/webtoons/$weekDay 요청")

        return webtoonService.getWeekdayWebtoons(weekDay).let {
            ApiResponse.ok(
                WeekdayWebtoonsResponse(
                    listOf(NAVER.toSite(), DAUM.toSite()),
                    it.convertToWeekDayWebtoons(),
                )
            )
        }
    }
 }
