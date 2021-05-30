package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "AccountController")
class AccountController(
    private val accountAuthService: AccountAuthService
) {
    @GetMapping("/api/v1/enroll")
    @ApiImplicitParams(
        ApiImplicitParam(name = "deviceId", value = "deviceId", required = true)
    )
    fun getJwtToken(@RequestParam deviceId: String):ApiResponse<String>{
        return accountAuthService.enrollAccount(deviceId)
    }
}
