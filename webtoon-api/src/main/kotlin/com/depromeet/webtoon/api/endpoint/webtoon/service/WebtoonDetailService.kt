package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.api.endpoint.dto.convertToWebtoonDetailResponse
import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
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

        foundWebtoon.orElseThrow {
            log.info("[WebtoonDetailService - getWebtoonDetail] wrong webtoonID -> $id")
            throw ApiValidationException("잘못된 웹툰 ID 입니다")
        }

        val rating = webtoonRatingAverageRepository.findByWebtoon(foundWebtoon.get())

        val comments = commentRepository.findRecent5Comments(foundWebtoon.get())

        val randomWebtoons = webtoonRepository.findRandomWebtoons()

        return ApiResponse.ok(convertToWebtoonDetailResponse(foundWebtoon.get(), rating, comments, randomWebtoons))
    }

    companion object {
        val log = LoggerFactory.getLogger(WebtoonImportService::class.java)
    }
}

