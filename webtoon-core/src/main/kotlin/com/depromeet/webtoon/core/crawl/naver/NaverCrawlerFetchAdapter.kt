package com.depromeet.webtoon.core.crawl.naver

import com.depromeet.webtoon.core.application.imports.dto.WebtoonImportRequest
import com.depromeet.webtoon.core.crawl.daum.DaumCrawlerService
import com.depromeet.webtoon.core.type.BackgroundColor
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NaverCrawlerFetchAdapter() {
    fun crawl(): List<WebtoonImportRequest> {
        // 네이버 웹툰의 정보를 긁어온다!
        val titleIdRegex = "titleId=(\\d*)".toRegex()

        return WEEKDAYS
            .map { weekday ->
                val doc: Document = Jsoup.connect("https://comic.naver.com/webtoon/weekdayList.nhn?week=$weekday").get()
                val webtoons: Elements = doc.select(".list_area ul li")
                webtoons.mapIndexed { index, it ->
                    val title = it.select("dl dt a").text()
                    val url = "https://comic.naver.com" + it.select("dl dt a").attr("href")
                    val score = it.select("dl .rating_type strong").text().toDouble()

                    val titleId = titleIdRegex.find(url)!!.groupValues[1]
                    NaverWebtoonItem(titleId.toLong(), title, weekday, score, index, url)
                }
            }
            .flatten()
            .fold(NaverWebtoonItems()) { acc, naverWebtoonItem -> acc.addItem(naverWebtoonItem); acc }
            .setDetailEach { item ->
                Thread.sleep(30)
                val doc: Document = Jsoup.connect(item.url).get()
                val webtoonDetail: Elements = doc.select(".comicinfo")
                val authors = webtoonDetail.select("div.detail span.wrt_nm").text().split("/").map { it.trim() }
                val thumbnailImage = webtoonDetail.select(".thumb img").attr("src")
                val genres = webtoonDetail.select(".detail_info .genre").text().split(",").map { it.trim() }
                val summary = webtoonDetail.select("h2").next("p").text()

                item.putDetail(authors, thumbnailImage, genres, summary)
                log.info("[NaverCrawlerService] $item")
            }.toWebtoonImportRequests()
    }

    class NaverWebtoonItems {
        private val items = mutableMapOf<Long, NaverWebtoonItem>()

        fun addItem(item: NaverWebtoonItem) {
            if (items.contains(item.titleId)) {
                items.getValue(item.titleId).addWeekday(item.weekdays)
            } else {
                items[item.titleId] = item
            }
        }

        fun setDetailEach(action: (NaverWebtoonItem) -> Unit): NaverWebtoonItems {
            items.values.forEach(action)
            return this
        }

        fun toWebtoonImportRequests(): List<WebtoonImportRequest> {
            return items.values.map {
                WebtoonImportRequest(
                    title = it.title,
                    authors = it.authors,
                    score = it.score,
                    thumbnailImage = it.thumbnailImage,
                    url = it.url,
                    weekdays = it.weekdays.map { str ->
                        convertToWeekDay(str)
                    },
                    site = WebtoonSite.NAVER,
                    genres = it.genres,
                    popular = it.rank,
                    summary = it.summary,
                    // todo 백그라운드 색상, 연재중
                    backgroundColor = BackgroundColor.NONE,
                    isComplete = false,
                 )
            }
        }

        private fun convertToWeekDay(str: String) = when (str) {
            "mon" -> WeekDay.MON
            "tue" -> WeekDay.TUE
            "wed" -> WeekDay.WED
            "thu" -> WeekDay.THU
            "fri" -> WeekDay.FRI
            "sat" -> WeekDay.SAT
            "sun" -> WeekDay.SUN
            else -> throw IllegalStateException()
        }
    }

    class NaverWebtoonItem(titleId: Long, title: String, weekday: String, score: Double, rank: Int, url: String) {
        val titleId: Long = titleId
        val title: String = title
        val weekdays: MutableSet<String> = mutableSetOf(weekday)
        val score: Double = score
        val rank: Int = rank
        val url: String = url

        // 상세정보는 상세페이지에서 처리
        lateinit var authors: List<String>
        lateinit var thumbnailImage: String
        lateinit var genres: List<String>
        lateinit var summary: String

        fun addWeekday(weekdays: Set<String>) {
            this.weekdays.addAll(weekdays)
        }

        fun putDetail(authors: List<String>, thumbnailImage: String, genres: List<String>, summary: String) {
            this.authors = authors
            this.thumbnailImage = thumbnailImage
            this.genres = genres
            this.summary = summary
        }
    }

    companion object {
        val log = LoggerFactory.getLogger(DaumCrawlerService::class.java)
        val WEEKDAYS = listOf("mon")
    }
}
