package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse

data class WebtoonSearchResponse(
    val query: String,
    val webtoons: List<WebtoonResponse>
)
