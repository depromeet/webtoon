package com.depromeet.webtoon.core.support

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.spring.SpringListener
import io.kotest.spring.SpringTestListener

/**
 * https://kotest.io/project_config
 */
object KotestIntegrationConfig : AbstractProjectConfig() {
    override fun listeners(): List<SpringTestListener> = listOf(SpringListener)
}
