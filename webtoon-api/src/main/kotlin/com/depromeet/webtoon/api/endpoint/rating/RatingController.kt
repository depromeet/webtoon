package com.depromeet.webtoon.api.endpoint.rating

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.rating.dto.VoteRequest
import com.depromeet.webtoon.api.endpoint.rating.dto.VoteResponse
import com.depromeet.webtoon.api.endpoint.rating.service.RatingImportService
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@Api("RatingController")
@RequestMapping("/api/v1/rating")
class RatingController(val ratingImportService: RatingImportService) {

    @PostMapping("/upsert")
    fun upsertWebtoonScore(@RequestBody voteRequest: VoteRequest): ApiResponse<VoteResponse> {
        return ratingImportService.upsertWebtoonScore(voteRequest)
    }
}
