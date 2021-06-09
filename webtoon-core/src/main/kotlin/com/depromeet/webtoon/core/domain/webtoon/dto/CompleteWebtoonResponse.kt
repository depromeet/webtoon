package com.depromeet.webtoon.core.domain.webtoon.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse

data class CompleteWebtoonResponse(
    val webtoons: List<WebtoonResponse>
)

fun List<WebtoonResponse>.convertToCompleteWebtoonResponse() =
    CompleteWebtoonResponse(
        webtoons = this
    )
