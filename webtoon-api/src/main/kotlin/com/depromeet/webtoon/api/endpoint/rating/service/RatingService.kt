package com.depromeet.webtoon.api.endpoint.rating.service

import com.depromeet.webtoon.api.endpoint.rating.dto.VoteRequest
import com.depromeet.webtoon.api.endpoint.rating.dto.convertVoteRequestToRating
import com.depromeet.webtoon.api.endpoint.rating.vo.OriginScore
import com.depromeet.webtoon.core.domain.rating.repository.RatingRepository
import org.springframework.stereotype.Service

@Service
class RatingService(
    val ratingRepository: RatingRepository
) {

    fun upsertWebtoonScore(voteRequest: VoteRequest): OriginScore {
        val optionalRating = ratingRepository.findByWebtoonIdAndAccountId(voteRequest.webtoonId, voteRequest.accountId)

        if (optionalRating == null) { // insert
            convertVoteRequestToRating(voteRequest).let { ratingRepository.save(it) }
            return OriginScore(false, voteRequest.storyScore, voteRequest.drawingScore)
        } else {
            val originScore = OriginScore(true, optionalRating.storyScore!!, optionalRating.drawingScore!!)
            optionalRating.apply {
                storyScore = voteRequest.storyScore
                drawingScore = voteRequest.drawingScore
            }
            return originScore
        }
    }
}
