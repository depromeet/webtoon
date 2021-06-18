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
    fun fetchHome(request: HomeApiRequest): HomeApiResponse {
        val weekDay = WeekDay.findByDayOfWeek(request.baseDateTime.dayOfWeek)
        val weekdayWebtoons = webtoonService.getWeekdayWebtoons(weekDay)
            .take(5)
            .convertToWebtoonResponses()

        val homeMainBanners = SearchBannerRequest(BannerInventory.HOME_MAIN, request.baseDateTime).let {
            bannerService.searchBanners(it).convertToBannerResponses()
        }

        val genreWebtoons = genreRecommendService.getRecommendWebtoonByGenre()
            .convertToWebtoonResponses()

        val recommendedAuthors = authorRecommendService.getHomeApiRecommendAuthors()

        // 완성을 위한.. 하드코딩
        // 84  #1F1B2C 다음웹툰 [승리호]
        // 414 #20689B 네이버웹툰 [3cm 헌터]
        // 423 #5B97BD 다음웹툰 [나빌레라]
        val bingeWatchableWebtoons = webtoonService.getWebtoons(listOf(84, 414, 423)).convertToWebtoonResponses()

        // 트렌딩 웹툰은 랜덤으로 웹툰 선정
        // TODO 수정 필요
        val trendingWebtoons = webtoonService.getRandomWebtoons().take(5).convertToWebtoonResponses()

        return HomeApiResponse(
            mainBanner = homeMainBanners,
            weekdayWebtoons = weekdayWebtoons,
            trendingWebtoons = trendingWebtoons,
            genreWebtoons = genreWebtoons,
            bingeWatchableWebtoons = bingeWatchableWebtoons,
            recommendAuthors = recommendedAuthors
        )
    }
}
