package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.type.WebtoonSite
import org.springframework.stereotype.Service

@Service
class WebtoonDetailService(
    private val webtoonRepository: WebtoonRepository,
) {

    fun getWebtoonDetail(site: WebtoonSite, title: String) : ApiResponse<WebtoonDetailResponse>  {

        val foundWebtoon = webtoonRepository.findBySiteAndTitle(site, title)

        // todo review 정보 찾아오기 1. 스토리 점수 평균 / 작화 점수 평균 / 코멘트 paging
        val webtoonDetailResponse =
            WebtoonDetailResponse(
                foundWebtoon!!.id!!,
                foundWebtoon.title,
                foundWebtoon.thumbnail,
                foundWebtoon.authors,
                foundWebtoon.site,
                foundWebtoon.weekdays,
                foundWebtoon.summary)
        return ApiResponse.ok(webtoonDetailResponse)
    }
}
