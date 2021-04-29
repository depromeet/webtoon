package com.depromeet.webtoon.api.endpoint.dto

import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val status: HttpStatus,
    val message: String?,
    val data: T?
)
