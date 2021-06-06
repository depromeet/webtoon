package com.depromeet.webtoon.crawl.config

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OkHttpConfig {

    @Bean
    fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}
