package com.depromeet.webtoon.batch.job.naver

import com.depromeet.webtoon.batch.job.naver.NaverWebtoonJobConfiguration.Companion.NAVER_WEBTOON_UPDATE_JOB
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
@ConditionalOnProperty(name = ["job.name"], havingValue = NAVER_WEBTOON_UPDATE_JOB)
class NaverWebtoonJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val naverWebtoonCrawler: NaverWebtoonCrawler,
    val webtoonImportService: WebtoonImportService,
) {
    private val log = LoggerFactory.getLogger(NaverWebtoonJobConfiguration::class.java)

    @Bean
    fun updateNaverWebtoons(
        @Qualifier("naverWebtoonImportStep") webtoonImportStep: Step
    ): Job {
        return jobBuilderFactory.get("naverWebtoonUpdateJob")
            .incrementer(ParamCleanRunIdIncrementer())
            .start(webtoonImportStep)
            .build()
    }

    @Bean
    @JobScope
    fun naverWebtoonImportStep(): Step {
        return stepBuilderFactory.get(this::naverWebtoonImportStep.name)
            .tasklet { _, _ ->
                val webtoons = naverWebtoonCrawler.crawlOngoingWebtoons()
                webtoons.map { webtoonImportService.importWebtoon(it) }
                RepeatStatus.FINISHED
            }
            .build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(NaverWebtoonJobConfiguration::class.java)
        const val NAVER_WEBTOON_UPDATE_JOB = "naverWebtoonUpdateJob"
    }
}
