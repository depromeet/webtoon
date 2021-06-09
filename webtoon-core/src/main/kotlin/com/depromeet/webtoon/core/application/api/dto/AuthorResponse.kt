package com.depromeet.webtoon.core.application.api.dto

import com.depromeet.webtoon.core.domain.author.model.Author

data class AuthorResponse(
    var id: Long,
    var name: String,
)

fun Author.convertToAuthorResponse() = AuthorResponse(
    this.id!!,
    this.name
)

fun List<Author>.convertToAuthorResponses() = this.map { it.convertToAuthorResponse() }
