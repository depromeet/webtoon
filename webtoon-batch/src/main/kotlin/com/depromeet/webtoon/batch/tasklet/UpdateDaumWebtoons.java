package com.depromeet.webtoon.batch.tasklet;

import com.depromeet.webtoon.core.crawl.daum.DaumCrawlerService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateDaumWebtoons implements Tasklet, InitializingBean {

    @Autowired
    DaumCrawlerService daumCrawlerService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        daumCrawlerService.updateDaumWebtoons();
        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
