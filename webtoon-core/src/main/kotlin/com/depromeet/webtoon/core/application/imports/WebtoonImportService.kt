package com.depromeet.webtoon.core.application.imports

import com.depromeet.webtoon.core.application.imports.dto.WebtoonImportRequest
import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.domain.author.service.AuthorService
import com.depromeet.webtoon.core.domain.webtoon.dto.WebtoonUpsertRequest
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WebtoonImportService(
    val webtoonService: WebtoonService,
    val authorService: AuthorService,

) {
    @Transactional(readOnly = false)
    fun importWebtoon(importRequest: WebtoonImportRequest): Webtoon {
        val authors = importRequest.authors.let { authorService.findOrCreateAuthors(it) }
        val webtoon = webtoonService.upsertWebtoon(toWebtoonUpsertRequest(importRequest, authors))

        log.info("[WebtoonImportService] webtoon upserted. webtoon title: ${webtoon.title} site: ${webtoon.site}")

        return webtoon
    }

    @Transactional(readOnly = false)
    fun importWebtoons(importRequests: List<WebtoonImportRequest>): List<Webtoon> {
        val authorMap = importRequests.map { it.authors }
            .flatten()
            .let { authorService.findOrCreateAuthors(it) }
            .associateBy { it.name }

        return importRequests
            .map {
                val authors = it.authors.map { rawAuthor -> authorMap.getValue(rawAuthor) }
                toWebtoonUpsertRequest(it, authors)
            }
            .map {
                webtoonService.upsertWebtoon(it)
            }
    }

    private fun toWebtoonUpsertRequest(
        importRequest: WebtoonImportRequest,
        authors: List<Author>
    ) = WebtoonUpsertRequest(
        title = importRequest.title,
        site = importRequest.site,
        authors = authors,
        dayOfWeeks = importRequest.weekdays,
        popularity = importRequest.popular,
        thumbnail = importRequest.thumbnailImage,
        summary = importRequest.summary,
        url = importRequest.url
    )

    companion object {
        val log = LoggerFactory.getLogger(WebtoonImportService::class.java)
    }
}
