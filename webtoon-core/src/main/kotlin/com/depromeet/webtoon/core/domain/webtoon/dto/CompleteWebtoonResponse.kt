package com.depromeet.webtoon.core.domain.webtoon.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse

data class CompleteWebtoonResponse(
    val isLast: Boolean,
    val lastWebtoonId: Long?,
    val webtoons: List<WebtoonResponse>
)

fun List<WebtoonResponse>.convertToCompleteWebtoonResponse(pageSize: Long) =
    CompleteWebtoonResponse(
        isLast = (this.size != 21),
        lastWebtoonId = this[lastIndex-1].id,
        webtoons = this.subList(0, pageSize.toInt())
    )
