package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.api.endpoint.dto.convertToWebtoonDetailResponse
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponse
import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import com.depromeet.webtoon.core.domain.rating.dto.CommentDto
import com.depromeet.webtoon.core.domain.rating.dto.ScoreDto
import com.depromeet.webtoon.core.domain.rating.repository.WebtoonRatingAverageRepository
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WebtoonDetailService(
    private val webtoonRepository: WebtoonRepository,
    private val webtoonRatingAverageRepository: WebtoonRatingAverageRepository,
    private val commentRepository: CommentRepository
) {

    fun getWebtoonDetail(id: Long): ApiResponse<WebtoonDetailResponse> {

        val foundWebtoon = webtoonRepository.findById(id)
        if (foundWebtoon.isEmpty) {
            log.info("[WebtoonDetailService - getWebtoonDetail] wrong webtoonID -> ${id}")
            throw ApiValidationException("잘못된 웹툰 ID 입니다")
        }

        val rating = webtoonRatingAverageRepository.findByWebtoon(foundWebtoon.get())

        val comments = commentRepository.findTop5ByWebtoonOrderByCreatedAtDesc(foundWebtoon.get())

        val randomWebtoons = webtoonRepository.findRandomWebtoons()

        if (rating != null) {
            val webtoonDetailResponse = convertToWebtoonDetailResponse(
                foundWebtoon.get().convertToWebtoonResponse(),
                ScoreDto(
                    rating.totalAverage,
                    rating.storyAverage,
                    rating.drawingAverage
                ),
                comments,
                randomWebtoons
            )
            log.info("[WebtoonDetailService - getWebtoonDetail] webtoon[${id}] rating not exist")
            return ApiResponse.ok(webtoonDetailResponse)
        } else {
            val webtoonDetailResponse = convertToWebtoonDetailResponse(
                foundWebtoon.get().convertToWebtoonResponse(),
                ScoreDto(
                    0.0,
                    0.0,
                    0.0
                ),
                comments,
                randomWebtoons
            )
            log.info("[WebtoonDetailService - getWebtoonDetail] webtoon[${id}] rating exist")
            return ApiResponse.ok(webtoonDetailResponse)
        }

    }

    companion object {
        val log = LoggerFactory.getLogger(WebtoonImportService::class.java)
    }
}

