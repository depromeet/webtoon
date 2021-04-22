package com.depromeet.webtoon.core.crawl.daum

import com.depromeet.webtoon.core.application.imports.dto.WebtoonImportRequest
import com.depromeet.webtoon.core.crawl.daum.dto.webtoonlist.DaumWebtoonCrawlResult
import org.springframework.stereotype.Component
import java.util.*

@Component
class DaumRequestAdapter {

    // TODO 수정 필요!
    fun rawWebtoonListToWebtoonImportRequest(daumWebtoonCrawlResult: DaumWebtoonCrawlResult): List<WebtoonImportRequest> {
        val rawWebtoonList = daumWebtoonCrawlResult.data.webtoons
        val date = Calendar.DAY_OF_WEEK

/*
        return rawWebtoonList.map { rawWebtoon ->
            WebtoonImportRequest(
                title = rawWebtoon.title,
                dayOfWeeks = listOf(DayOfWeek.MONDAY, DayOfWeek.FRIDAY),
                authors = rawWebtoon.cartoon.artists.map {  it.name }.distinct(),
                site = WebtoonSite.DAUM,




                *//*List<Author>
                rawWebtoon.cartoon.artists.map { artist -> Author(artist.name, emptyList()) }
                    .distinctBy { author -> author.name }*//*
            )
        }*/
        return emptyList()
    }
}
