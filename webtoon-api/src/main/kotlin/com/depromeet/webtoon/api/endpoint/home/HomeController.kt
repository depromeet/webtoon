package com.depromeet.webtoon.api.endpoint.home

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.application.api.home.HomeApiService
import com.depromeet.webtoon.core.application.api.home.dto.HomeApiRequest
import com.depromeet.webtoon.core.application.api.home.dto.HomeApiResponse
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("api/v1")
class HomeController(val homeApiService: HomeApiService) {

    @GetMapping("/home")
    @ApiImplicitParams(
        ApiImplicitParam(name = "Authorization",
            value = "authorization header",
            required = true,
            dataType = "string",
            paramType = "header",
            defaultValue = "Bearer testToken")
    )
    fun home(
        @RequestParam(name = "baseDateTime", required = false)
        baseDateTime: LocalDateTime?
    ): ApiResponse<HomeApiResponse> {
        val homeApiRequest = HomeApiRequest(baseDateTime = baseDateTime ?: LocalDateTime.now())
        val homeApiResponse = homeApiService.fetchHome(homeApiRequest)

        return ApiResponse.ok(homeApiResponse)
    }
}
