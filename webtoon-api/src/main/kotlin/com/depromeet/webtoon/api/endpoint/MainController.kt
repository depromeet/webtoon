package com.depromeet.webtoon.api.endpoint

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {
    @GetMapping("/")
    fun root(): Map<String, String> {
        return mapOf("hello" to "world")
    }
}
