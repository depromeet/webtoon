package com.depromeet.webtoon.api.endpoint.author

import com.depromeet.webtoon.api.endpoint.author.service.AuthorRecommendService
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.domain.author.dto.AuthorRecommendResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorController(val authorRecommendService: AuthorRecommendService) {

    @GetMapping("/api/v1/authors")
    fun recommendAuthors():ApiResponse<AuthorRecommendResponse>{
        return ApiResponse.ok(authorRecommendService.getRecommendAuthors())
    }
}
