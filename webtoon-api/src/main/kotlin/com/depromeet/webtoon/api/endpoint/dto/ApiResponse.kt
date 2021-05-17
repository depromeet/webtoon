package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.api.type.ApiResponseStatus
import com.depromeet.webtoon.api.type.ApiResponseStatus.*

data class ApiResponse<T>(
    val status: ApiResponseStatus,
    val message: String?,
    val data: T?
) {
    companion object {
        fun <T> ok(data: T) = ApiResponse(OK, null, data)
        fun serverError(message: String) = ApiResponse(SERVER_ERROR, message, null)
        fun clientError(message: String) = ApiResponse(CLIENT_ERROR, message, null)
    }
}
