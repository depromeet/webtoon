package com.depromeet.webtoon.api.endpoint.home

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.home.dto.HomeApiResponse
import com.depromeet.webtoon.api.endpoint.home.dto.TopBannerItem
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
import com.depromeet.webtoon.core.type.WeekDay
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class HomeController(val webtoonService: WebtoonService) {

    @GetMapping("/home")
    fun home(): ApiResponse<HomeApiResponse> {
        val sampleWebtoons = webtoonService.getWeekdayWebtoons(WeekDay.MON)
            .take(3)
            .convertToWebtoonResponses()

        val homeApiResponse = HomeApiResponse(
            topBanner = sampleWebtoons.map { TopBannerItem(it, "드라마 원작 웹툰") },
            weekdayWebtoons = sampleWebtoons,
            trendingWebttons = sampleWebtoons,
            genreWebtoons = sampleWebtoons,
            bingeWatchableWebtoons = sampleWebtoons,
        )

        return ApiResponse.ok(homeApiResponse)
    }
}
