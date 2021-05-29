package com.depromeet.webtoon.api.endpoint.dto

data class JwtDto(
    var key: String,
    var value: String
)

fun convertToJwtDto(token: String): JwtDto{
    return JwtDto(
        key = "Authorization",
        value = "Bearer $token"
    )
}
