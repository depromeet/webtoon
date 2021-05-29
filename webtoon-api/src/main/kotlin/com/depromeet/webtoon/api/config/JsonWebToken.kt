package com.depromeet.webtoon.api.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "json.token")
data class JsonWebToken(
    val subject: String,
    val expiredTime: Int,
    val secure: String,
)
