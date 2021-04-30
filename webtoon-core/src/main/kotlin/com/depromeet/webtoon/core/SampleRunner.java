package com.depromeet.webtoon.core;

import com.depromeet.webtoon.core.crawl.daum.DaumCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

// 테스트를 위해 크롤링하여 데이터 삽입하는 환경 구성
@Component
public class SampleRunner implements ApplicationRunner {

    @Autowired
    DaumCrawlerService daumCrawlerService;


    @Override
    public void run(ApplicationArguments args) {
        daumCrawlerService.updateDaumWebtoons();
    }
}
