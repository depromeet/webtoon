package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.api.endpoint.dto.convertToWebtoonDetailResponse
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import org.springframework.stereotype.Service

@Service
class WebtoonDetailService(
    private val webtoonRepository: WebtoonRepository,
    private val reviewRepository: ReviewRepository
) {

    fun getWebtoonDetail(id: Long): ApiResponse<WebtoonDetailResponse> {

        val foundWebtoon = webtoonRepository.findById(id).get()

        val scores = reviewRepository.getScores(foundWebtoon)
        val comments = reviewRepository.getComments(foundWebtoon)

        val webtoonDetailResponse = convertToWebtoonDetailResponse(foundWebtoon, scores, comments)

        return ApiResponse.ok(webtoonDetailResponse)
    }
}
