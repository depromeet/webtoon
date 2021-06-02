package com.depromeet.webtoon.api.common.authexception

import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ToonietoonieAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        throw ApiValidationException("잘못된 토큰")
    }
}
