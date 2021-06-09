package com.depromeet.webtoon.api.endpoint.author

import com.depromeet.webtoon.api.common.annotation.SwaggerAuthApi
import com.depromeet.webtoon.core.domain.author.service.AuthorRecommendService
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.domain.author.dto.AuthorRecommendResponse
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api("authorController")
class AuthorController(val authorRecommendService: AuthorRecommendService) {

    @GetMapping("/api/v1/authors")
    @SwaggerAuthApi
    fun recommendAuthors():ApiResponse<AuthorRecommendResponse>{
        return ApiResponse.ok(authorRecommendService.getRecommendAuthors())
    }
}
