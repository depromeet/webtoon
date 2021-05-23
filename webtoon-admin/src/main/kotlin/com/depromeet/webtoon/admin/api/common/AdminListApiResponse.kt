package com.depromeet.webtoon.admin.api.common

data class AdminListApiResponse<T>(
    val totalElements: Long,
    val content: List<T>
)
