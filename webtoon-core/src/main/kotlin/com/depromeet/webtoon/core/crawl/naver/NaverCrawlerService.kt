package com.depromeet.webtoon.core.crawl.naver

import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.core.application.imports.dto.WebtoonImportRequest
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
class NaverCrawlerService(
    val webtoonImportService: WebtoonImportService
) {
    fun crawl() {
        // 네이버 웹툰의 정보를 긁어온다!
        // 모바일페이지? 데스크탑?? 이건 보도록 하자
        val doc: Document = Jsoup.connect("https://comic.naver.com/webtoon/weekdayList.nhn?week=mon").get()

        val webtoons: Elements = doc.select(".list_area ul li")
        val refinedWebtoons = webtoons.mapIndexed { index, it ->
            WebtoonImportRequest(
                title = it.select("dl dt a").text(),
                authors = it.select("dl .desc").text().split("/"),
                score = it.select("dl .rating_type strong").text().toDouble(),
                thumbnailImage = it.select(".thumb img").attr("src"),
                url = it.select("dl dt a").attr("href"),
                dayOfWeeks = listOf(WeekDay.MON),
                site = WebtoonSite.NAVER,
                genres = listOf(),
                popular = index
            )
        }

        refinedWebtoons.forEach {
            webtoonImportService.importWebtoon(it)
        }
    }
}
