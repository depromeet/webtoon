package com.depromeet.webtoon.core;

import com.depromeet.webtoon.core.crawl.daum.DaumCrawlerService;
import com.depromeet.webtoon.core.crawl.naver.NaverCrawlerService;
import com.depromeet.webtoon.core.domain.account.model.Account;
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository;
import com.depromeet.webtoon.core.domain.review.model.Review;
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository;
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon;
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

// 테스트를 위해 크롤링하여 데이터 삽입하는 환경 구성
@Component
@Profile("webtoon-local-test")
public class SampleRunner implements ApplicationRunner {

    @Autowired
    NaverCrawlerService naverCrawlerService;

    @Autowired
    DaumCrawlerService daumCrawlerService;

    @Autowired
    WebtoonRepository webtoonRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public void run(ApplicationArguments args) {

        daumCrawlerService.updateDaumWebtoons();
        naverCrawlerService.crawlAndUpsert();

        Optional<Webtoon> webtoon = webtoonRepository.findById(1l);

        Account account = new Account(null, "testDeviceId", "tester", LocalDateTime.now(), LocalDateTime.now());
        accountRepository.save(account);

        Review review = new Review(null, webtoon.get(), account, "재밌다", 3.0, 5.0, LocalDateTime.now(), LocalDateTime.now());
        reviewRepository.save(review);

    }
}
