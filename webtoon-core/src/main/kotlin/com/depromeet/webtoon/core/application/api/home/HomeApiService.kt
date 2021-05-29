package com.depromeet.webtoon.core.application.api.home

import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.application.api.home.dto.HomeApiRequest
import com.depromeet.webtoon.core.application.api.home.dto.HomeApiResponse
import com.depromeet.webtoon.core.application.common.dto.BannerResponse.Companion.convertToBannerResponses
import com.depromeet.webtoon.core.domain.banner.dto.SearchBannerRequest
import com.depromeet.webtoon.core.domain.banner.model.BannerType
import com.depromeet.webtoon.core.domain.banner.service.BannerService
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
import com.depromeet.webtoon.core.type.WeekDay
import org.springframework.stereotype.Service

@Service
class HomeApiService(
    val webtoonService: WebtoonService,
    val bannerService: BannerService,
) {
    fun fetchHome(homeApiRequest: HomeApiRequest): HomeApiResponse {
        val sampleWebtoons = webtoonService.getWeekdayWebtoons(WeekDay.MON)
            .take(3)
            .convertToWebtoonResponses()

        val homeMainBanners = SearchBannerRequest(BannerType.HOME_MAIN, homeApiRequest.baseDateTime).let {
            bannerService.searchBanners(it).convertToBannerResponses()
        }

        return HomeApiResponse(
            mainBanner = homeMainBanners,
            weekdayWebtoons = sampleWebtoons,
            trendingWebtons = sampleWebtoons,
            genreWebtoons = sampleWebtoons,
            bingeWatchableWebtoons = sampleWebtoons,
        )
    }
}
