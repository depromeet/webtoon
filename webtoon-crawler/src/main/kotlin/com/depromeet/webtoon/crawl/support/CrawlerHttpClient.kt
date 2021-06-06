package com.depromeet.webtoon.crawl.support

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component

/**
 * simple crawling http client
 * this class may have low performance
 * no connection pool, no timeout..
 */
@Component
class CrawlerHttpClient(
    private val httpClient: OkHttpClient,
    private val objectMapper: ObjectMapper
) {

    fun <T> getEntity(url: String, mappingClass: Class<T>): T {
        val request = Request.Builder().get()
            .header("Content-Type", "application/json")
            .url(url)
            .build()

        return httpClient.newCall(request).execute().let {
            objectMapper.readValue(it.body!!.string(), mappingClass)
        }
    }
}
