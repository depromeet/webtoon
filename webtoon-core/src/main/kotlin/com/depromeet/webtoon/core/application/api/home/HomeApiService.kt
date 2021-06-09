package com.depromeet.webtoon.core.application.api.home

import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.application.api.home.dto.HomeApiRequest
import com.depromeet.webtoon.core.application.api.home.dto.HomeApiResponse
import com.depromeet.webtoon.core.application.common.dto.BannerResponse.Companion.convertToBannerResponses
import com.depromeet.webtoon.core.domain.author.service.AuthorRecommendService
import com.depromeet.webtoon.core.domain.banner.dto.SearchBannerRequest
import com.depromeet.webtoon.core.domain.banner.model.BannerInventory
import com.depromeet.webtoon.core.domain.banner.service.BannerService
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
import org.springframework.stereotype.Service

@Service
class HomeApiService(
    val webtoonService: WebtoonService,
    val bannerService: BannerService,
    val genreRecommendService: GenreRecommendService,
    val authorRecommendService: AuthorRecommendService,
) {
    fun fetchHome(homeApiRequest: HomeApiRequest): HomeApiResponse {
        val sampleWebtoons = webtoonService.getWeekdayWebtoons(WeekDay.MON)
            .take(3)
            .convertToWebtoonResponses()

        val homeMainBanners = SearchBannerRequest(BannerInventory.HOME_MAIN, homeApiRequest.baseDateTime).let {
            bannerService.searchBanners(it).convertToBannerResponses()
        }

        val genreWebtoons = genreRecommendService.getRecommendWebtoonByGenre()
            .convertToWebtoonResponses()

        val recommendedAuthors = authorRecommendService.getHomeApiRecommendAuthors()

        return HomeApiResponse(
            mainBanner = homeMainBanners,
            weekdayWebtoons = sampleWebtoons,
            trendingWebtoons = sampleWebtoons,
            genreWebtoons = genreWebtoons,
            bingeWatchableWebtoons = sampleWebtoons,
            recommendAuthors = recommendedAuthors
        )
    }
}
