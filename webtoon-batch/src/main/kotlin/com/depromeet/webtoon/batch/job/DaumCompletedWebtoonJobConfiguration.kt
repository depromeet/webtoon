package com.depromeet.webtoon.batch.job

import com.depromeet.webtoon.batch.job.DaumCompletedWebtoonJobConfiguration.Companion.DAUM_COMPLETED_WEBTOON_UPDATE_JOB
import com.depromeet.webtoon.batch.support.ParamCleanRunIdIncrementer
import com.depromeet.webtoon.core.crawl.daum.DaumCrawlerService
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
@ConditionalOnProperty(name = ["job.name"], havingValue = DAUM_COMPLETED_WEBTOON_UPDATE_JOB)
class DaumCompletedWebtoonJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val daumCrawlerService: DaumCrawlerService
) {
    private val log = LoggerFactory.getLogger(DaumCompletedWebtoonJobConfiguration::class.java)

    @Bean
    fun updateDaumCompletedWebtoons(
        @Qualifier("daumCompletedWebtoonImportStep") webtoonImportStep: Step
    ): Job {
        return jobBuilderFactory.get("daumCompletedWebtoonImportStep")
            .incrementer(ParamCleanRunIdIncrementer())
            .start(webtoonImportStep)
            .build()
    }

    @Bean
    @JobScope
    fun daumCompletedWebtoonImportStep(@Value("#{jobParameters[requestDate]}")requestDate: String?  ): Step {
        return stepBuilderFactory.get("daumCompletedWebtoonImportStep")
            .tasklet { contribution, chunckContext ->
                log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@STEP@@@@@@@")
                daumCrawlerService.updateCompletedDaumWebtoons()
                log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@STEP@@@@@@@")
                RepeatStatus.FINISHED
            }
            .build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(DaumCompletedWebtoonJobConfiguration::class.java)
        const val DAUM_COMPLETED_WEBTOON_UPDATE_JOB = "daumCompletedWebtoonUpdateJob"
    }
}
