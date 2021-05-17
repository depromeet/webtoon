package com.depromeet.webtoon.api.endpoint.dto

data class WebtoonSearchResponse(
    val query: String,
    val webtoons: List<WebtoonResponse>
)
