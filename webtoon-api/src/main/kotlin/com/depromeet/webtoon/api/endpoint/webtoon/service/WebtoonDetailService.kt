package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.api.endpoint.dto.convertToWebtoonDetailResponse
import com.depromeet.webtoon.core.domain.rating.dto.ScoreDto
import com.depromeet.webtoon.core.domain.rating.repository.RatingRepository
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.stereotype.Service

@Service
class WebtoonDetailService(
    private val webtoonRepository: WebtoonRepository,
    private val ratingRepository: RatingRepository
) {

    fun getWebtoonDetail(id: Long): ApiResponse<WebtoonDetailResponse> {

        val foundWebtoon = webtoonRepository.findById(id)
        if (foundWebtoon.isEmpty) {
            throw ApiValidationException("잘못된 웹툰 ID 입니다")
        }

        val review = ratingRepository.findByWebtoon(foundWebtoon.get())

        val webtoonDetailResponse: WebtoonDetailResponse

        return if (review == null) {
            webtoonDetailResponse = convertToWebtoonDetailResponse(foundWebtoon.get(), ScoreDto(0.0, 0.0), emptyList())
            ApiResponse.ok(webtoonDetailResponse)
        } else {
            val scores = ratingRepository.getScores(foundWebtoon.get())
            webtoonDetailResponse = convertToWebtoonDetailResponse(foundWebtoon.get(), scores, emptyList())
            ApiResponse.ok(webtoonDetailResponse)
        }
    }
}
