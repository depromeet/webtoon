package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.domain.account.dto.AccountLoginRequest
import com.depromeet.webtoon.core.domain.account.dto.AccountLoginResult
import io.swagger.annotations.Api
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
    fun loginWithToken(@RequestBody loginRequest: AccountLoginRequest):ApiResponse<AccountLoginResult>{
        return accountAuthService.loginAccount(loginRequest.loginToken)
    }
}
