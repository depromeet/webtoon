package com.depromeet.webtoon.core.domain.webtoon.dto

import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse

data class CompleteWebtoonResponse(
    val isLast: Boolean,
    val lastWebtoonId: Long?,
    val webtoons: List<WebtoonResponse>
)

fun List<WebtoonResponse>.convertToCompleteWebtoonResponse(pageSize: Long):CompleteWebtoonResponse{
    return CompleteWebtoonResponse(
        isLast = (this.size != pageSize.toInt()+1), /// 1001 개고 pageSize 1000
        lastWebtoonId =
        if(this.size != pageSize.toInt()+1){
            null
        } else {
            this[lastIndex-1].id
        },
        webtoons = this.subList(0, pageSize.toInt())
    )
}
