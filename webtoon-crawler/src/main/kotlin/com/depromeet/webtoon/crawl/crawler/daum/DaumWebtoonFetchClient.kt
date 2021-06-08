package com.depromeet.webtoon.crawl.crawler.daum

import com.depromeet.webtoon.crawl.crawler.daum.dto.webtoondetail.DaumWebtoonDetailCrawlResult
import com.depromeet.webtoon.crawl.crawler.daum.dto.webtoonlist.CompleteDaumWebtoonCrawlResult
import com.depromeet.webtoon.crawl.crawler.daum.dto.webtoonlist.DaumWebtoonCrawlResult
import com.depromeet.webtoon.crawl.support.CrawlerHttpClient
import org.springframework.stereotype.Component
import java.util.Calendar

@Component
class DaumWebtoonFetchClient(
    private val httpClient: CrawlerHttpClient
) {

    // 완결 웹툰 nicknames 크롤링
    fun crawlCompletedWebtoonNicknames(): List<String> {
        val crawlResult = httpClient.getEntity(
            "$DAUM_WEBTOON_ROOT_URL/data/pc/webtoon/list_finished/free",
            CompleteDaumWebtoonCrawlResult::class.java
        )
        return crawlResult.data.filter { it.ageGrade != 19 }.map { it.nickname }
    }

    // 웹툰 상세 정보 ( genre 정보를 위해 필요 )
    fun crawlWebtoonDetail(nickname: String): DaumWebtoonDetailCrawlResult? {
        return httpClient.getEntity(
            "$DAUM_WEBTOON_ROOT_URL/data/mobile/webtoon/view?nickname=$nickname",
            DaumWebtoonDetailCrawlResult::class.java
        )
    }

    fun crawlCompletedWebtoonDetail(nickname: String): DaumWebtoonDetailCrawlResult? {
        return httpClient.getEntity(
            "$DAUM_WEBTOON_ROOT_URL/data/mobile/webtoon/view?nickname=$nickname",
            DaumWebtoonDetailCrawlResult::class.java
        )
    }

    // 오늘 업데이트 된 웹툰 nicknames 가져오기
    fun getTodayUpdatedNicknames(): List<String> {
        val currentWeekDay = getWeekday()
        val currentTime = System.currentTimeMillis()
        val result = getCrawlResultOfTodayUpdated(currentWeekDay, currentTime)
        return result!!.data.webtoons.filterNot { it.ageGrade == 19 }.map { it.nickname }
    }

    // 요일 반환
    private fun getWeekday(): String {
        val weekDay = arrayOf("sun", "mon", "tue", "wed", "thu", "fri", "sat")
        return weekDay[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1]
    }

    // 다음웹툰 업데이트 순 크롤링
    private fun getCrawlResultOfTodayUpdated(
        currentWeekday: String,
        currentTime: Long
    ): DaumWebtoonCrawlResult? {
        return httpClient.getEntity(
            "$DAUM_WEBTOON_ROOT_URL/data/mobile/webtoon?sort=update&page_size=50&week=$currentWeekday&page_no=1&$currentTime",
            DaumWebtoonCrawlResult::class.java
        )
    }

    // 인기순 웹툰의 nickname 반환 ( for webtoonDetail crawl )
    fun getPopularNicknames(): List<String> {
        val popularWebtoonNicknames = mutableListOf<String>()
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
        return httpClient.getEntity(
            "$DAUM_WEBTOON_ROOT_URL/data/mobile/webtoon?sort=view&page_size=50&page_no=$pageNum&$currentTime",
            DaumWebtoonCrawlResult::class.java
        )
    }

    companion object {
        const val DAUM_WEBTOON_ROOT_URL = "http://m.webtoon.daum.net"
    }
}
