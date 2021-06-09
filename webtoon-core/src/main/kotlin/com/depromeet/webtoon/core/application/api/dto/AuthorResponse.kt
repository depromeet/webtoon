package com.depromeet.webtoon.core.application.api.dto

import com.depromeet.webtoon.core.domain.author.model.Author

data class AuthorResponse(
    var id: Long,
    var name: String,
    var authorImage: String?,
)

fun Author.convertToAuthorResponse() = AuthorResponse(
    this.id!!,
    this.name,
    authorImage = randomAuthorImage()

)

fun List<Author>.convertToAuthorResponses() = this.map { it.convertToAuthorResponse() }

private fun randomAuthorImage(): String {

    val imageList = listOf(
        "https://iili.io/BQbI9f.png",
        "https://iili.io/BQbnFn.png",
        "https://iili.io/BQboas.png",
        "https://iili.io/BQbx8G.png",
        "https://iili.io/BQbTu4.png",
        "https://iili.io/BQbuwl.png"
    )

    return imageList[(Math.random()*imageList.size).toInt()]
}


