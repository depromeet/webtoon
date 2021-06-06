package com.depromeet.webtoon.batch.job.daum

import com.depromeet.webtoon.batch.job.daum.DaumWebtoonJobConfiguration.Companion.DAUM_WEBTOON_UPDATE_JOB
import com.depromeet.webtoon.batch.support.ParamCleanRunIdIncrementer
import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.crawl.crawler.daum.DaumWebtoonCrawler
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["job.name"], havingValue = DAUM_WEBTOON_UPDATE_JOB)
class DaumWebtoonJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val daumWebtoonCrawler: DaumWebtoonCrawler,
    val webtoonImportService: WebtoonImportService,
) {
    private val log = LoggerFactory.getLogger(DaumWebtoonJobConfiguration::class.java)

    @Bean
    fun updateDaumWebtoons(
        @Qualifier("webtoonImportStep") webtoonImportStep: Step
    ): Job {
        return jobBuilderFactory.get("daumWebtoonUpdateJob")
            .incrementer(ParamCleanRunIdIncrementer())
            .start(webtoonImportStep)
            .build()
    }

    @Bean
    @JobScope
    fun webtoonImportStep(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return stepBuilderFactory.get("webtoonImportStep")
            .tasklet { _, _ ->
                log.info("=====test$requestDate======")

                val ongoingWebtoons = daumWebtoonCrawler.crawlOngoingWebtoons()
                ongoingWebtoons.map { webtoonImportService.importWebtoon(it) }

                log.info("=====test$requestDate======")
                RepeatStatus.FINISHED
            }
            .build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(DaumWebtoonJobConfiguration::class.java)
        const val DAUM_WEBTOON_UPDATE_JOB = "daumWebtoonUpdateJob"
    }
}
