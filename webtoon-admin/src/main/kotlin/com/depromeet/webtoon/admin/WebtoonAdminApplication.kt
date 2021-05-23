package com.depromeet.webtoon.admin

import com.depromeet.webtoon.core.WebtoonCoreRoot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [WebtoonAdminApplication::class, WebtoonCoreRoot::class])
class WebtoonAdminApplication

fun main(args: Array<String>) {
    runApplication<WebtoonAdminApplication>(*args)
}
