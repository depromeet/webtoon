package com.depromeet.webtoon.core.domain.webtoon.service

import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.domain.webtoon.dto.WebtoonCreateRequest
import com.depromeet.webtoon.core.domain.webtoon.dto.WebtoonCreateResponseDto
import com.depromeet.webtoon.core.domain.webtoon.dto.WebtoonTop20Response
import com.depromeet.webtoon.core.domain.webtoon.dto.WebtoonUpsertRequest
import com.depromeet.webtoon.core.domain.webtoon.dto.toWebtoonCreateResponseDto
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.type.WeekDay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WebtoonService(
    val webtoonRepository: WebtoonRepository
) {
    private val log = LoggerFactory.getLogger(WebtoonService::class.java)

    fun getTop20WebtoonsByGenre(genre: String): WebtoonTop20Response {
        log.info("$genre service")
        val top20 = mutableListOf<Webtoon>()
        top20.addAll(webtoonRepository.get_Top10_Naver_Webtoons_ByGenre(genre))
        top20.addAll(webtoonRepository.get_Top10_Daum_Webtoons_ByGenre(genre))
        return WebtoonTop20Response(
            genre = genre,
            top20Webtoons = top20.convertToWebtoonResponses()
        )
    }

    fun findById(id: Long): Webtoon {
        return webtoonRepository.findById(id)
            .orElseThrow { IllegalArgumentException("매칭되는 webtoon을 찾지 못했습니다. id: $id") }
    }

    fun getWeekdayWebtoons(weekDay: WeekDay): List<Webtoon> {
        return webtoonRepository.findAllByWeekdaysOrderByPopularityAsc(weekDay)
    }

    fun getWebtoons(): List<WebtoonCreateResponseDto> {
        val webtoon = webtoonRepository.findAll()

        return webtoon.map { it.toWebtoonCreateResponseDto() }
    }

    fun createWebtoon(request: WebtoonCreateRequest): WebtoonCreateResponseDto {
        val newWebtoon = Webtoon(title = request.title, authors = request.authors)
        val savedNewWebtoon = webtoonRepository.save(newWebtoon)
        return savedNewWebtoon.toWebtoonCreateResponseDto()
    }

    fun upsertWebtoon(request: WebtoonUpsertRequest): Webtoon {
        val optionalWebtoon = webtoonRepository.findBySiteAndTitle(request.site, request.title)
        // 이미 존재하는 경우 내부 데이터를 업데이트합니다.
        return if (optionalWebtoon != null) {
            optionalWebtoon.apply {
                site = request.site
                title = request.title
                authors = request.authors.toMutableList()
                weekdays = request.dayOfWeeks.toMutableList()
                popularity = request.popularity
                thumbnail = request.thumbnail
                summary = request.summary
                genres = request.genres.toMutableList()
                url = request.url
                score = request.score
                backgroudColor = request.backgroundColor
                isComplete = request.isComplete
            }
        }
        // 존재하지 않는 경우 새롭게 데이터를 입력한 다음 저장
        else {
            Webtoon(
                title = request.title,
                site = request.site,
                authors = request.authors,
                weekdays = request.dayOfWeeks.toMutableList(),
                popularity = request.popularity,
                thumbnail = request.thumbnail,
                summary = request.summary,
                genres = request.genres.toMutableList(),
                url = request.url,
                score = request.score,
                backgroundColor = request.backgroundColor,
                isComplete = request.isComplete,
            ).let {
                webtoonRepository.save(it)
            }
        }
    }

    fun getRandomWebtoons(): List<Webtoon> {
        return webtoonRepository.find20RandomWebtoons()
    }


}
