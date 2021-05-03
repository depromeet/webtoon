package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.api.type.ApiResponseStatus

data class ApiResponse<T>(
    val status: ApiResponseStatus,
    val message: String?,
    val data: T?
) {
    companion object {
        fun <T> ok(data: T) = ApiResponse(ApiResponseStatus.OK, null, data)
    }
}
