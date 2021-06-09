package com.depromeet.webtoon.crawl.crawler.daum

import com.depromeet.webtoon.common.dto.imports.WebtoonImportRequest
import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.crawl.crawler.daum.dto.webtoondetail.WebtoonWeeks
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

// API 서비스 호출 + 정제
@Component
class DaumWebtoonCrawler(
    val fetchClient: DaumWebtoonFetchClient
) {
    fun crawlCompletedWebtoons(): List<WebtoonImportRequest> {
        val completedWebtoons = fetchClient.crawlCompletedWebtoonNicknames()
            .map { fetchClient.crawlCompletedWebtoonDetail(it) }

        return completedWebtoons.map {
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
                backgroundColor = "",
                isComplete = true
            )
        }
    }

    fun crawlOngoingWebtoons(): List<WebtoonImportRequest> {
        val updatedWebtoons = crawlSortedWebtoonNicknames()
            .map { fetchClient.crawlWebtoonDetail(it) }

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
                "",
                isComplete = false,
            )
        }

        log.info("[DaumCrawlerService] - updateDaumWebtoons() complete")

        return webtoonImportRequests
    }

    fun crawlSortedWebtoonNicknames(): List<String> {
        val popularNicknames = fetchClient.getPopularNicknames()
        val updatedNicknames = fetchClient.getTodayUpdatedNicknames()

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
        val log = LoggerFactory.getLogger(DaumWebtoonCrawler::class.java)
        const val DAUM_WEBTOON_URL = "http://webtoon.daum.net/webtoon/view/"
    }
}
