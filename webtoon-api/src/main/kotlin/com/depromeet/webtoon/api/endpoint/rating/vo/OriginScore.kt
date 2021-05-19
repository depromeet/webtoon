package com.depromeet.webtoon.api.endpoint.rating.vo

data class OriginScore(
    var isUpdate: Boolean,
    val originStoryScore: Double,
    val originDrawingScore: Double
)
