package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.type.WebtoonSite
import org.springframework.stereotype.Service

@Service
class WebtoonDetailService(
    private val webtoonRepository: WebtoonRepository,
    private val reviewRepository: ReviewRepository
) {

    fun getWebtoonDetail(site: WebtoonSite, title: String) : ApiResponse<WebtoonDetailResponse>  {

        val foundWebtoon = webtoonRepository.findBySiteAndTitle(site, title)

        val drawingScore = reviewRepository.getAvgDrawingScore(foundWebtoon!!)
        val storyScore = reviewRepository.getAvgStoryScore(foundWebtoon)
        val comments = reviewRepository.getComments(foundWebtoon)


        val webtoonDetailResponse =
            WebtoonDetailResponse(
                foundWebtoon.id!!,
                foundWebtoon.title,
                foundWebtoon.thumbnail,
                foundWebtoon.url,
                foundWebtoon.authors,
                foundWebtoon.site,
                foundWebtoon.weekdays,
                foundWebtoon.summary,
                drawingScore,
                storyScore,
                comments
            )
        return ApiResponse.ok(webtoonDetailResponse)
    }
}
