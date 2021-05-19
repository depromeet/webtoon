package com.depromeet.webtoon.api.endpoint.rating.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.rating.dto.VoteRequest
import com.depromeet.webtoon.api.endpoint.rating.dto.VoteResponse
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RatingImportService(
    val webtoonRepository: WebtoonRepository,
    val accountRepository: AccountRepository,
    val ratingService: RatingService,
    val webtoonRatingAverageService: WebtoonRatingAverageService,
) {

    @Transactional(readOnly = false)
    fun upsertWebtoonScore(voteRequest: VoteRequest): ApiResponse<VoteResponse> {

        // validate webtoonId
        val optionalWebtoon = webtoonRepository.findById(voteRequest.webtoonId)
        if (optionalWebtoon.isEmpty) {
            throw ApiValidationException("잘못된 웹툰ID 입니다")
        }

        // validate accountId
        val optionalAccount = accountRepository.findById(voteRequest.accountId)
        if (optionalAccount.isEmpty) {
            throw ApiValidationException("잘못된 사용자ID 입니다")
        }

        // rating upsert
        val originScore = ratingService.upsertWebtoonScore(voteRequest)
        // webtoonRatingAverage upsert
        return webtoonRatingAverageService.upsertWebtoonScore(voteRequest, originScore)
    }
}
