package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.api.endpoint.dto.convertToWebtoonDetailResponse
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.stereotype.Service

@Service
class WebtoonDetailService(
    private val webtoonRepository: WebtoonRepository,
    private val reviewRepository: ReviewRepository
) {

    fun getWebtoonDetail(id: Long): ApiResponse<WebtoonDetailResponse> {

        val foundWebtoon = webtoonRepository.findById(id)
        if (foundWebtoon.isEmpty) {
            throw ApiValidationException("잘못된 웹툰 ID 입니다")
        }

        reviewRepository.findByWebtoon(foundWebtoon.get()) ?: throw ApiValidationException("해당 웹툰의 리뷰가 존재하지 않습니다")

        val scores = reviewRepository.getScores(foundWebtoon.get())
        val comments = reviewRepository.getComments(foundWebtoon.get())
        val webtoonDetailResponse = convertToWebtoonDetailResponse(foundWebtoon.get(), scores, comments)
        return ApiResponse.ok(webtoonDetailResponse)
    }
}
