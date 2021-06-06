package com.depromeet.webtoon.batch.job.sample

import com.depromeet.webtoon.batch.job.sample.SampleJobConfiguration.Companion.SAMPLE_JOB_NAME
import com.depromeet.webtoon.batch.support.ParamCleanRunIdIncrementer
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

// @Configuration
@ConditionalOnProperty(name = ["job.name"], havingValue = SAMPLE_JOB_NAME)
class SampleJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory
) {
    private val log = LoggerFactory.getLogger(SampleJobConfiguration::class.java)

    @Bean
    fun sampleJob(
        @Qualifier("sampleStep") sampleStep: Step
    ): Job {
        return jobBuilderFactory.get("sampleJob")
            .incrementer(ParamCleanRunIdIncrementer())
            .start(sampleStep)
            .build()
    }

    @Bean
    @JobScope
    fun sampleStep(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return stepBuilderFactory.get("sampleStep")
            .tasklet { contribution, chunckContext ->
                log.info("helloWorld $requestDate")
                RepeatStatus.FINISHED
            }
            .build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(SampleJobConfiguration::class.java)
        const val SAMPLE_JOB_NAME = "sampleJob"
    }
}
