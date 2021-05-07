package com.depromeet.webtoon.batch.job

import com.depromeet.webtoon.batch.job.SampleJobConfiguration.Companion.SAMPLE_JOB_NAME
import com.depromeet.webtoon.batch.support.ParamCleanRunIdIncrementer
import com.depromeet.webtoon.core.crawl.daum.DaumCrawlerService
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["spring.batch.job.names"], havingValue = SAMPLE_JOB_NAME)
class SampleJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val crawlerService: DaumCrawlerService
) {
    private val log = LoggerFactory.getLogger(SampleJobConfiguration::class.java)

    @Bean
    fun sampleJob(): Job {
        return jobBuilderFactory.get("sampleJob")
            .incrementer(ParamCleanRunIdIncrementer())
            .start(sampleStep(null))
            .build()
    }

    @Bean
    @JobScope
    fun sampleStep(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return stepBuilderFactory.get("sampleStep")
            .tasklet { contribution, chunckContext ->
                log.info("helloWorld $requestDate")
                crawlerService.updateDaumWebtoons()
                RepeatStatus.FINISHED
            }
            .build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(SampleJobConfiguration::class.java)
        const val SAMPLE_JOB_NAME = "sampleJob"
    }
}
