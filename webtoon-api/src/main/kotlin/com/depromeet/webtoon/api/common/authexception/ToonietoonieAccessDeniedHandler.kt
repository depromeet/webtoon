package com.depromeet.webtoon.api.common.authexception

import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ToonietoonieAccessDeniedHandler: AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        throw ApiValidationException("권한이 없습니다.")
    }
}
