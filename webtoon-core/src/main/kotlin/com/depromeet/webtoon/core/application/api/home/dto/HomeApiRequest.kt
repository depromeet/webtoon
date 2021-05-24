package com.depromeet.webtoon.core.application.api.home.dto

import io.swagger.annotations.ApiModel
import java.time.LocalDateTime

@ApiModel("홈화면 API 요청")
data class HomeApiRequest(
    val baseDateTime: LocalDateTime = LocalDateTime.now()
)
