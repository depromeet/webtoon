package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.domain.account.AccountLoginResult
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "AccountController")
class AccountController(
    private val accountAuthService:
    AccountAuthService
) {
    @PostMapping("/api/v1/login")
    fun loginWithToken(@RequestBody loginToken: String):ApiResponse<AccountLoginResult>{
        return accountAuthService.loginAccount(loginToken)
    }
}
