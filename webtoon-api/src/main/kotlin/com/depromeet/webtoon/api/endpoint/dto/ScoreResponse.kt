package com.depromeet.webtoon.api.endpoint.dto

data class ScoreResponse(
    val totalScore: Double,
    var storyScore: Double,
    var drawingScore: Double
)
