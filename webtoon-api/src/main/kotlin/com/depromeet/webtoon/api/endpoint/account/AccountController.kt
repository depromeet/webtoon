package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.api.common.annotation.SwaggerAuthApi
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse.Companion.ok
import com.depromeet.webtoon.core.domain.account.dto.AccountLoginRequest
import com.depromeet.webtoon.core.domain.account.dto.AccountResponse
import com.depromeet.webtoon.core.domain.account.dto.NicknameUpdateResponse
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "AccountController")
class AccountController(
    private val authService: AccountAuthService,
    private val updateService: AccountUpdateService
) {
    @PostMapping("/api/v1/login")
    fun loginWithToken(@RequestBody loginRequest: AccountLoginRequest):ApiResponse<AccountResponse>{
        return ok(authService.loginAccount(loginRequest.loginToken))
    }

    @PatchMapping("/api/v1/nickname")
    @SwaggerAuthApi
    fun updateNickname(@RequestParam newNickname: String): ApiResponse<NicknameUpdateResponse> {
        return ok(updateService.updateNickname(newNickname))
    }
}
