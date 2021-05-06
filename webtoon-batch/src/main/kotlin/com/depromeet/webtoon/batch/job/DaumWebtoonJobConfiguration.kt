package com.depromeet.webtoon.batch.job

import com.depromeet.webtoon.batch.job.DaumWebtoonJobConfiguration.Companion.DAUM_WEBTOON_UPDATE_JOB
import com.depromeet.webtoon.batch.support.ParamCleanRunIdIncrementer
import com.depromeet.webtoon.batch.tasklet.UpdateDaumWebtoons
import com.depromeet.webtoon.core.crawl.daum.DaumCrawlerService
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["spring.batch.job.names"], havingValue = DAUM_WEBTOON_UPDATE_JOB)
class DaumWebtoonJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val updateDaumWebtoon: UpdateDaumWebtoons
) {
    private val log = LoggerFactory.getLogger(DaumWebtoonJobConfiguration::class.java)

    @Bean
    fun updateDaumWebtoons(
        @Qualifier("webtoonImportStep") webtoonImportStep: Step
    ): Job {
        return jobBuilderFactory.get("daumUpdateJob")
            .incrementer(ParamCleanRunIdIncrementer())
            .start(webtoonImportStep)
            .build()
    }

    @Bean
    @JobScope
    fun webtoonImportStep(): Step {
        log.info("updateDaumWebtoon step executed")
        return stepBuilderFactory.get("webtoonImportStep")
            .tasklet(updateDaumWebtoon)
            .build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(DaumWebtoonJobConfiguration::class.java)
        const val DAUM_WEBTOON_UPDATE_JOB = "daumWebtoonUpdateJob"
    }
}
