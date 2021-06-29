package com.depromeet.webtoon.batch.job.daum

import com.depromeet.webtoon.batch.job.daum.DaumCompletedWebtoonJobConfiguration.Companion.DAUM_COMPLETED_WEBTOON_UPDATE_JOB
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["job.name"], havingValue = DAUM_COMPLETED_WEBTOON_UPDATE_JOB)
class DaumCompletedWebtoonJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val daumWebtoonCrawler: DaumWebtoonCrawler,
    val webtoonImportService: WebtoonImportService,
) {

    @Bean
    fun updateDaumCompletedWebtoons(): Job {
        return jobBuilderFactory.get("daumCompletedWebtoonUpdateJob")
            .incrementer(ParamCleanRunIdIncrementer())
            .start(daumCompletedWebtoonImportStep())
            .build()
    }

    @Bean
    @JobScope
    fun daumCompletedWebtoonImportStep(): Step {
        return stepBuilderFactory.get("daumCompletedWebtoonImportStep")
            .tasklet { contribution, chunckContext ->
                val ongoingWebtoons = daumWebtoonCrawler.crawlCompletedWebtoons()
                ongoingWebtoons.map { webtoonImportService.importWebtoon(it) }
                RepeatStatus.FINISHED
            }
            .build()
    }

    companion object {
        const val DAUM_COMPLETED_WEBTOON_UPDATE_JOB = "daumCompletedWebtoonUpdateJob"
    }
}
