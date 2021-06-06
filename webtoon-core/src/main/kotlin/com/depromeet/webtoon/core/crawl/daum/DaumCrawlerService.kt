package com.depromeet.webtoon.core.crawl.daum

import com.depromeet.webtoon.common.dto.imports.WebtoonImportRequest
import com.depromeet.webtoon.common.type.BackgroundColor
import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.core.crawl.daum.dto.webtoondetail.WebtoonWeeks
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

// API 서비스 호출 + 정제
@Service
class DaumCrawlerService(
    val webtoonImportService: WebtoonImportService,
    val fetchService: DaumCrawlerFetchService
) {

    fun updateCompletedDaumWebtoons() {
        val completedWebtoons = fetchService.crawlCompletedWebtoonNicknames()
            .map { fetchService.crawlCompletedWebtoonDetail(it) }

        val completeWebtoonImportRequests = completedWebtoons.map {
            WebtoonImportRequest(
                title = it!!.data.webtoon.title,
                url = DAUM_WEBTOON_URL + it.data.webtoon.nickname,
                thumbnailImage = it.data.webtoon.thumbnailImage2.url,
                weekdays = listOf(WeekDay.NONE),
                authors = it.data.webtoon.cartoon.artists.map { it.name }.distinct(),
                site = WebtoonSite.DAUM,
                popular = 0,
                summary = it.data.webtoon.introduction,
                genres = it.data.webtoon.cartoon.genres.map { it.name },
                score = it.data.webtoon.averageScore,
                backgroundColor = BackgroundColor.NONE,
                isComplete = true
            )
        }

        completeWebtoonImportRequests.map { webtoonImportService.importWebtoon(it) }
    }

    fun updateDaumWebtoons() {
        val updatedWebtoons = crawlSortedWebtoonNicknames()
            .map { fetchService.crawlWebtoonDetail(it) }

        val webtoonImportRequests = updatedWebtoons.mapIndexed { idx, crawled ->
            WebtoonImportRequest(
                crawled!!.data.webtoon.title,
                DAUM_WEBTOON_URL + crawled.data.webtoon.nickname,
                crawled.data.webtoon.thumbnailImage2.url,
                setDayOfWeek(crawled.data.webtoon.webtoonWeeks!!),
                crawled.data.webtoon.cartoon.artists.map { it.name }.distinct(),
                WebtoonSite.DAUM,
                crawled.data.webtoon.cartoon.genres.map { it.name },
                crawled.data.webtoon.averageScore,
                idx + 1,
                crawled.data.webtoon.introduction,
                // todo 배경화면, 연재중 확인할 것
                BackgroundColor.NONE,
                isComplete = false,
            )
        }

        webtoonImportRequests.map { webtoonImportService.importWebtoon(it) }

        log.info("[DaumCrawlerService] - updateDaumWebtoons() complete")
    }

    fun crawlSortedWebtoonNicknames(): List<String> {
        val popularNicknames = fetchService.getPopularNicknames()
        val updatedNicknames = fetchService.getTodayUpdatedNicknames()

        val popular = popularNicknames.filter { it in updatedNicknames }
        val unpopular = updatedNicknames.filterNot { it in popularNicknames }

        return popular + unpopular
    }

    fun setDayOfWeek(list: List<WebtoonWeeks>): List<WeekDay> {
        val dayOfWeeks = ArrayList<WeekDay>()
        list.forEach {
            if (it.weekDay == "mon") dayOfWeeks.add(WeekDay.MON)
            if (it.weekDay == "tue") dayOfWeeks.add(WeekDay.TUE)
            if (it.weekDay == "wed") dayOfWeeks.add(WeekDay.WED)
            if (it.weekDay == "thu") dayOfWeeks.add(WeekDay.THU)
            if (it.weekDay == "fri") dayOfWeeks.add(WeekDay.FRI)
            if (it.weekDay == "sat") dayOfWeeks.add(WeekDay.SAT)
            if (it.weekDay == "sun") dayOfWeeks.add(WeekDay.SUN)
        }
        return dayOfWeeks
    }

    companion object {
        val log = LoggerFactory.getLogger(DaumCrawlerService::class.java)
        const val DAUM_WEBTOON_URL = "http://webtoon.daum.net/webtoon/view/"
    }
}
