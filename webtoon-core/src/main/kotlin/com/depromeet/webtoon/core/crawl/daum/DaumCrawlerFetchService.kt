package com.depromeet.webtoon.core.crawl.daum

import com.depromeet.webtoon.core.crawl.daum.dto.webtoondetail.DaumWebtoonDetailCrawlResult
import com.depromeet.webtoon.core.crawl.daum.dto.webtoonlist.CompleteDaumWebtoonCrawlResult
import com.depromeet.webtoon.core.crawl.daum.dto.webtoonlist.DaumWebtoonCrawlResult
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import java.util.*
import kotlin.collections.ArrayList


@Service
class DaumCrawlerFetchService {

    private val exchangeStrategies: ExchangeStrategies = ExchangeStrategies
        .builder()
        .codecs { it.defaultCodecs().maxInMemorySize(10 * 1024 * 1024) }
        .build()

    private val client: WebClient = WebClient.builder()
        .baseUrl(DAUM_WEBTOON_ROOT_URL)
        .exchangeStrategies(exchangeStrategies)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    // 완결 웹툰 nicknames 크롤링
    fun crawlCompletedWebtoonNicknames(): List<String>{
        val crawlResult = client.get()
            .uri("data/pc/webtoon/list_finished/free").retrieve()
            .bodyToMono(CompleteDaumWebtoonCrawlResult::class.java).block()



        return crawlResult!!.data.filter { it.ageGrade != 19 }.map { it.nickname }
    }

    // 웹툰 상세 정보 ( genre 정보를 위해 필요 )
    fun crawlWebtoonDetail(nickname: String): DaumWebtoonDetailCrawlResult? {
        return client.get()
            .uri("data/mobile/webtoon/view?nickname=$nickname").retrieve()
            .bodyToMono(DaumWebtoonDetailCrawlResult::class.java).block()
    }

    fun crawlCompletedWebtoonDetail(nickname: String): DaumWebtoonDetailCrawlResult? {
        return client.get()
            .uri("data/pc/webtoon/view/${nickname}").retrieve()
            .bodyToMono(DaumWebtoonDetailCrawlResult::class.java).block()
    }



    // 오늘 업데이트 된 웹툰 nicknames 가져오기
    fun getTodayUpdatedNicknames(): List<String> {
        val currentWeekDay = getWeekday()
        val currentTime = System.currentTimeMillis()
        val result = getCrawlResultOfTodayUpdated(client, currentWeekDay, currentTime)
        return result!!.data.webtoons.filterNot { it.ageGrade == 19 }.map { it.nickname }
    }

    // 요일 반환
    private fun getWeekday(): String {
        val weekDay = arrayOf("sun", "mon", "tue", "wed", "thu", "fri", "sat")
        return weekDay[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1]
    }

    // 다음웹툰 업데이트 순 크롤링
    private fun getCrawlResultOfTodayUpdated(
        client: WebClient,
        currentWeekday: String,
        currentTime: Long
    ): DaumWebtoonCrawlResult? {
        return client.get()
            .uri("data/mobile/webtoon?sort=update&page_size=50&week=$currentWeekday&page_no=1&$currentTime").retrieve()
            .bodyToMono(DaumWebtoonCrawlResult::class.java).block()
    }

    // 인기순 웹툰의 nickname 반환 ( for webtoonDetail crawl )
    fun getPopularNicknames(): ArrayList<String> {
        val popularWebtoonNicknames = ArrayList<String>()
        val currentTime = System.currentTimeMillis()
        // 인기 목록 1 ~ 50
        popularWebtoonNicknames.addAll(
            crawlPopularWebtoons(1, currentTime)!!
                .data.webtoons.filterNot { it.ageGrade == 19 }.map { it.nickname }
        )

        // 인기 목록 51 ~ 100
        popularWebtoonNicknames.addAll(
            crawlPopularWebtoons(2, currentTime)!!
                .data.webtoons.filterNot { it.ageGrade == 19 }.map { it.nickname }
        )
        return popularWebtoonNicknames
    }

    // 다음웹툰 인기 순 크롤링
    private fun crawlPopularWebtoons(
        pageNum: Int,
        currentTime: Long
    ): DaumWebtoonCrawlResult? {
        return client.get()
            .uri("data/mobile/webtoon?sort=view&page_size=50&page_no=$pageNum&$currentTime").retrieve()
            .bodyToMono(DaumWebtoonCrawlResult::class.java).block()
    }

    companion object {
        const val DAUM_WEBTOON_ROOT_URL = "http://m.webtoon.daum.net"
    }
}
