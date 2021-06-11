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
    "https://i.ibb.co/BLjqQpt/Img-random1.png",
    "https://i.ibb.co/Lx5TxXt/Img-random2.png",
    "https://i.ibb.co/YNjdKcc/Img-random3.png",
    "https://i.ibb.co/6g1DfvB/Img-random4.png",
    "https://i.ibb.co/JdctPLc/Img-random5.png",
    "https://i.ibb.co/Bc2gCgd/Img-random6.png"
    )

    return imageList[(Math.random()*imageList.size).toInt()]
}


