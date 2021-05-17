package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.api.type.ApiResponseStatus
import com.depromeet.webtoon.api.type.ApiResponseStatus.*

data class ApiResponse<T>(
    val status: ApiResponseStatus,
    val message: String?,
    val data: T?
) {
    companion object {
        fun <T> ok(data: T) = ApiResponse(ApiResponseStatus.OK, null, data)
        fun <T> emptyWebtoon(data: T?) = ApiResponse(ApiResponseStatus.EMPTY, "잘못된 웹툰 ID 입니다.", data)
        fun <T> emptyReview(data: T?) = ApiResponse(ApiResponseStatus.EMPTY, "해당 웹툰에 대한 리뷰가 없습니다.", data)
        fun serverError(message: String) = ApiResponse(SERVER_ERROR, message, null)
        fun clientError(message: String) = ApiResponse(CLIENT_ERROR, message, null)
    }
}
