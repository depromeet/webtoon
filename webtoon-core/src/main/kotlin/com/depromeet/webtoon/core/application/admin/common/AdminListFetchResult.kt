package com.depromeet.webtoon.core.application.admin.common

data class AdminListFetchResult<T>(val content: List<T>, val totalElements: Long)
