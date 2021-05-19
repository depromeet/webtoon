package com.depromeet.webtoon.api.endpoint.rating.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.rating.dto.VoteRequest
import com.depromeet.webtoon.api.endpoint.rating.dto.VoteResponse
import com.depromeet.webtoon.api.endpoint.rating.dto.convertVoteRequestToRatingAvg
import com.depromeet.webtoon.api.endpoint.rating.dto.firstVote
import com.depromeet.webtoon.api.endpoint.rating.dto.updateVote
import com.depromeet.webtoon.api.endpoint.rating.vo.OriginScore
import com.depromeet.webtoon.core.domain.rating.repository.WebtoonRatingAverageRepository
import org.springframework.stereotype.Service

@Service
class WebtoonRatingAverageService(
    val webtoonRatingAverageRepository: WebtoonRatingAverageRepository
) {
    fun upsertWebtoonScore(voteRequest: VoteRequest, originScore: OriginScore): ApiResponse<VoteResponse> {

        val optionalRatingAvg = webtoonRatingAverageRepository.findByWebtoonId(voteRequest.webtoonId)

        if (optionalRatingAvg == null) {
            convertVoteRequestToRatingAvg(voteRequest).let { webtoonRatingAverageRepository.save(it) }
            return ApiResponse.ok(firstVote(voteRequest))
        } else {
            if (originScore.isUpdate) {
                optionalRatingAvg.apply {
                    totalStoryScore = totalStoryScore?.minus(originScore.originStoryScore)?.plus(voteRequest.storyScore)
                    totalDrawingScore = totalDrawingScore?.minus(originScore.originDrawingScore)?.plus(voteRequest.drawingScore)
                    storyAverage = totalStoryScore?.div(votes!!)
                    drawingAverage = totalDrawingScore?.div(votes!!)
                    totalAverage = storyAverage?.plus(drawingAverage!!)?.div(2)
                }
                return ApiResponse.ok(updateVote(optionalRatingAvg))
            } else {
                optionalRatingAvg.apply {
                    totalStoryScore = totalStoryScore?.plus(voteRequest.storyScore)
                    totalDrawingScore = totalDrawingScore?.plus(voteRequest.drawingScore)
                    votes = votes?.plus(1)
                    storyAverage = totalStoryScore?.div(votes!!)
                    drawingAverage = totalDrawingScore?.div(votes!!)
                    totalAverage = storyAverage?.plus(drawingAverage!!)?.div(2)
                }
                return ApiResponse.ok(updateVote(optionalRatingAvg))
            }
        }
    }
}
