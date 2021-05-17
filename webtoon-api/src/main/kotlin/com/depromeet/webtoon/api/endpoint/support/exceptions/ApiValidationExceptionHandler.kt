package com.depromeet.webtoon.api.endpoint.support.exceptions

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ApiValidationExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ApiValidationException::class])
    fun handleValidationExceptions(
        ex: ApiValidationException,
        request: WebRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        val clientError = ApiResponse.clientError(ex.message ?: "")
        return ResponseEntity.badRequest().body(clientError)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleDefaultExceptions(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        val clientError = ApiResponse.clientError(ex.message ?: "")
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(clientError)
    }
}
