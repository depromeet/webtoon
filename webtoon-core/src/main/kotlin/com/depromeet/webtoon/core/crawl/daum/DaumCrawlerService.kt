package com.depromeet.webtoon.core.crawl.daum

import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.core.application.imports.dto.WebtoonImportRequest
import com.depromeet.webtoon.core.crawl.daum.dto.webtoondetail.DaumWebtoonDetailCrawlResult
import com.depromeet.webtoon.core.crawl.daum.dto.webtoondetail.WebtoonWeeks
import com.depromeet.webtoon.core.crawl.daum.dto.webtoonlist.DaumWebtoonCrawlResult
import com.depromeet.webtoon.core.type.WebtoonSite
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate

// API 서비스 호출 + 정제
@Service
class DaumCrawlerService(val webtoonImportService: WebtoonImportService) {

    private val client: WebClient = WebClient.builder()
        .baseUrl("http://m.webtoon.daum.net")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    private val currentTime = Instant.now().toEpochMilli()
    private val currentWeekDay = LocalDate.now().dayOfWeek.name.toLowerCase().substring(0, 3)

    fun updateDaumWebtoons() {
        val todayWebtoonNicknamesSortedAsPopularity = getSortedNicknamesByPopularity()
        val webtoonImportRequests = getWebtoonImportRequests(todayWebtoonNicknamesSortedAsPopularity)

        val listIterator = webtoonImportRequests.listIterator()
        while (listIterator.hasNext()) {
            val importRequest = listIterator.next()
            webtoonImportService.importWebtoon(importRequest)
            log.info("[DaumCrawlerService] - webtoon import. webtoon title : ${importRequest.title}")
        }
    }

    private fun getWebtoonImportRequests(nicknames: List<String>): List<WebtoonImportRequest> {
        val webtoonImportRequests = ArrayList<WebtoonImportRequest>()
        fun crawlResultToWebtoonImportRequest(crawlResult: DaumWebtoonDetailCrawlResult, popular: Int): WebtoonImportRequest {

            fun setDayOfWeek(list: List<WebtoonWeeks>): List<DayOfWeek> {
                val dayOfWeeks = ArrayList<DayOfWeek>()
                list.forEach {
                    if (it.weekDay == "mon") dayOfWeeks.add(DayOfWeek.MONDAY)
                    if (it.weekDay == "tue") dayOfWeeks.add(DayOfWeek.TUESDAY)
                    if (it.weekDay == "wed") dayOfWeeks.add(DayOfWeek.WEDNESDAY)
                    if (it.weekDay == "thu") dayOfWeeks.add(DayOfWeek.THURSDAY)
                    if (it.weekDay == "fri") dayOfWeeks.add(DayOfWeek.FRIDAY)
                    if (it.weekDay == "sat") dayOfWeeks.add(DayOfWeek.SATURDAY)
                    if (it.weekDay == "sun") dayOfWeeks.add(DayOfWeek.SUNDAY)
                }
                return dayOfWeeks
            }

            return WebtoonImportRequest(
                crawlResult.data.webtoon.title,
                setDayOfWeek(crawlResult.data.webtoon.webtoonWeeks),
                crawlResult.data.webtoon.cartoon.artists.map { it.name }.distinct(),
                WebtoonSite.DAUM,
                crawlResult.data.webtoon.cartoon.genres.map { it.name },
                crawlResult.data.webtoon.averageScore,
                popular
            )
        }

        val listIterator = nicknames.listIterator()

        while (listIterator.hasNext()) {
            val crawlResult = client.get()
                .uri("data/mobile/webtoon/view?nickname=${listIterator.next()}").retrieve()
                .bodyToMono(DaumWebtoonDetailCrawlResult::class.java).block()

            val crawlResultToWebtoonImportRequest =
                crawlResultToWebtoonImportRequest(crawlResult!!, listIterator.nextIndex())

            // println(crawlResultToWebtoonImportRequest)
            webtoonImportRequests.add(crawlResultToWebtoonImportRequest)
        }
        return webtoonImportRequests
    }

    fun getSortedNicknamesByPopularity(): List<String> {
        val popularNicknames = getPopularNicknames()
        val todayNicknames = getTodayUpdatedNicknames()

        val todayPopulars = popularNicknames.filter { it in todayNicknames }
        val todayUnPopulars = todayNicknames.filterNot { it in popularNicknames }

        return todayPopulars + todayUnPopulars
    }

    private fun getPopularNicknames(): ArrayList<String> {
        val popularWebtoonNicknames = ArrayList<String>()
        // 인기 목록 1 ~ 50
        popularWebtoonNicknames.addAll(
            getCrawlResultOfPopular(1, client, currentTime)!!
                .data.webtoons.filterNot { it.ageGrade == 19 }.map { it.nickname }
        )

        // 인기 목록 51 ~ 100
        popularWebtoonNicknames.addAll(
            getCrawlResultOfPopular(2, client, currentTime)!!
                .data.webtoons.filterNot { it.ageGrade == 19 }.map { it.nickname }
        )
        return popularWebtoonNicknames
    }

    // 오늘 업데이트 된 웹툰 nicknames 가져오기
    private fun getTodayUpdatedNicknames(): List<String> {
        val result = getCrawlResultOfTodayUpdated(client, currentWeekDay, currentTime)
        return result!!.data.webtoons.filterNot { it.ageGrade == 19 }.map { it.nickname }
    }

    private fun getCrawlResultOfTodayUpdated(
        client: WebClient,
        currentWeekday: String,
        currentTime: Long
    ): DaumWebtoonCrawlResult? {
        return client.get()
            .uri("data/mobile/webtoon?sort=update&page_size=50&week=$currentWeekday&page_no=1&$currentTime").retrieve()
            .bodyToMono(DaumWebtoonCrawlResult::class.java).block()
    }

    private fun getCrawlResultOfPopular(
        pageNum: Int,
        client: WebClient,
        currentTime: Long
    ): DaumWebtoonCrawlResult? {
        return client.get()
            .uri("data/mobile/webtoon?sort=view&page_size=50&page_no=$pageNum&$currentTime").retrieve()
            .bodyToMono(DaumWebtoonCrawlResult::class.java).block()
    }

    companion object{
        val log = LoggerFactory.getLogger(DaumCrawlerService::class.java)
    }
}
