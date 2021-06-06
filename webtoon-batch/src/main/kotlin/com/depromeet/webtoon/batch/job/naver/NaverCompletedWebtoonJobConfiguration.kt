package com.depromeet.webtoon.batch.job.naver

import com.depromeet.webtoon.batch.job.naver.NaverCompletedWebtoonJobConfiguration.Companion.NAVER_COMPLETED_WEBTOON_UPDATE_JOB
import com.depromeet.webtoon.batch.support.ParamCleanRunIdIncrementer
import com.depromeet.webtoon.core.application.imports.WebtoonImportService
import com.depromeet.webtoon.crawl.crawler.naver.NaverWebtoonCrawler
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
@ConditionalOnProperty(name = ["job.name"], havingValue = NAVER_COMPLETED_WEBTOON_UPDATE_JOB)
class NaverCompletedWebtoonJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val naverWebtoonCrawler: NaverWebtoonCrawler,
    val webtoonImportService: WebtoonImportService,
) {

    @Bean
    fun updateNaverCompletedWebtoons(
        @Qualifier("naverCompletedWebtoonImportStep") webtoonImportStep: Step
    ): Job {
        return jobBuilderFactory.get(NAVER_COMPLETED_WEBTOON_UPDATE_JOB)
            .incrementer(ParamCleanRunIdIncrementer())
            .start(webtoonImportStep)
            .build()
    }

    @Bean
    @JobScope
    fun naverCompletedWebtoonImportStep(): Step {
        return stepBuilderFactory.get(this::naverCompletedWebtoonImportStep.name)
            .tasklet { _, _ ->
                val webtoons = naverWebtoonCrawler.crawlCompletedWebtoons()
                webtoons.map { webtoonImportService.importWebtoon(it) }
                RepeatStatus.FINISHED
            }
            .build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(NaverCompletedWebtoonJobConfiguration::class.java)
        const val NAVER_COMPLETED_WEBTOON_UPDATE_JOB = "naverCompletedWebtoonUpdateJob"
    }
}
