package com.depromeet.webtoon.api.common.authexception

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.type.ApiResponseStatus
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ToonietoonieAuthenticationEntryPoint(val objectMapper: ObjectMapper) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        log.info("UnAuthorizaed!!! message : {}", authException.message)

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.outputStream.println(
            objectMapper.writeValueAsString(
                ApiResponse(
                    ApiResponseStatus.CLIENT_ERROR,
                    "UNAUTHORIZED",
                    null
                )
            )
        )
    }

    companion object {
        val log = LoggerFactory.getLogger(ToonietoonieAuthenticationEntryPoint::class.java)
    }
}
