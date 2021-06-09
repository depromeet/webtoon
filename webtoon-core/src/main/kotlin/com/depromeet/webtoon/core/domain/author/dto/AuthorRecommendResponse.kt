package com.depromeet.webtoon.core.domain.author.dto

import com.depromeet.webtoon.core.application.api.dto.AuthorResponse

data class AuthorRecommendResponse(
    val authors: List<AuthorResponse>
)

fun List<AuthorResponse>.convertToAuthorRecommendResponse() =
    AuthorRecommendResponse(
        authors = this
    )
