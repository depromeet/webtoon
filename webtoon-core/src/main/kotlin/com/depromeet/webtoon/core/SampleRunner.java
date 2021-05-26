package com.depromeet.webtoon.core;

import com.depromeet.webtoon.core.crawl.daum.DaumCrawlerService;
import com.depromeet.webtoon.core.crawl.naver.NaverCrawlerService;
import com.depromeet.webtoon.core.domain.account.model.Account;
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository;
import com.depromeet.webtoon.core.domain.comment.model.Comment;
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository;
import com.depromeet.webtoon.core.domain.rating.model.Rating;
import com.depromeet.webtoon.core.domain.rating.model.WebtoonRatingAverage;
import com.depromeet.webtoon.core.domain.rating.repository.RatingRepository;
import com.depromeet.webtoon.core.domain.rating.repository.WebtoonRatingAverageRepository;
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon;
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository;
import com.depromeet.webtoon.core.type.WebtoonSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
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
    RatingRepository ratingRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    WebtoonRatingAverageRepository webtoonRatingAverageRepository;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {

        // daumCrawlerService.updateCompletedDaumWebtoons();
        daumCrawlerService.updateDaumWebtoons();
        // naverCrawlerService.crawlAndUpsert();

        Optional<Webtoon> webtoon = webtoonRepository.findById(1l);

        Account account = new Account(null, "testDeviceId", "tester", LocalDateTime.now(), LocalDateTime.now());
        accountRepository.save(account);

        Account account2 = new Account(null, "testDeviceId2", "tester2", LocalDateTime.now(), LocalDateTime.now());
        accountRepository.save(account2);

        Account account3 = new Account(null, "testDeviceId3", "tester3", LocalDateTime.now(), LocalDateTime.now());
        accountRepository.save(account3);

        Account account4 = new Account(null, "testDeviceId4", "tester4", LocalDateTime.now(), LocalDateTime.now());
        accountRepository.save(account4);

        Account account5 = new Account(null, "testDeviceId5", "tester5", LocalDateTime.now(), LocalDateTime.now());
        accountRepository.save(account5);

        Account account6 = new Account(null, "testDeviceId6", "tester6", LocalDateTime.now(), LocalDateTime.now());
        accountRepository.save(account6);


        Rating rating = new Rating(null, webtoon.get(), account, 3.0, 5.0, LocalDateTime.now(), LocalDateTime.now());
        ratingRepository.save(rating);


        Comment comment = new Comment(null, "재밌네요", account, webtoon.get(), account.getNickname(), LocalDateTime.now(), LocalDateTime.now());
        commentRepository.save(comment);


        Thread.sleep(1000);
        Comment comment2 = new Comment(null, "재밌네요2", account2, webtoon.get(), account.getNickname(), LocalDateTime.now(), LocalDateTime.now());
        commentRepository.save(comment2);
        Thread.sleep(1000);
        Comment comment3 = new Comment(null, "재밌네요3", account3, webtoon.get(), account.getNickname(), LocalDateTime.now(), LocalDateTime.now());
        commentRepository.save(comment3);
        Thread.sleep(1000);
        Comment comment4 = new Comment(null, "재밌네요4", account4, webtoon.get(), account.getNickname(), LocalDateTime.now(), LocalDateTime.now());
        commentRepository.save(comment4);
        Thread.sleep(1000);
        Comment comment5 = new Comment(null, "재밌네요5", account5, webtoon.get(), account.getNickname(), LocalDateTime.now(), LocalDateTime.now());
        commentRepository.save(comment5);
        Thread.sleep(1000);
        Comment comment6 = new Comment(null, "재밌네요6", account6, webtoon.get(), account.getNickname(), LocalDateTime.now(), LocalDateTime.now());
        commentRepository.save(comment6);



        WebtoonRatingAverage webtoonRatingAverage = new WebtoonRatingAverage(null, webtoon.get(), 3.0, 5.0, 1L, 3.0, 5.0, 4.0, LocalDateTime.now(), LocalDateTime.now());
        webtoonRatingAverageRepository.save(webtoonRatingAverage);


        System.out.println("=====");
        int randomNumber = (int) (Math.random() * 5);
        // 장르 리스트에서

        List<Webtoon> webtoons = genreRecommend("드라마", randomNumber);
        webtoons.stream().forEach(w -> System.out.println(w.getTitle()));
        System.out.println("=====");
        webtoonRepository.findTop5ByGenresInAndScoreGreaterThanAndSite(List.of("드라마"), 9, WebtoonSite.NAVER).stream().forEach(w -> System.out.println(w.getTitle()));
        webtoonRepository.findTop5ByGenresInAndScoreGreaterThanAndSite(List.of("드라마"), 9, WebtoonSite.DAUM).stream().forEach(w -> System.out.println(w.getTitle()));

    }

    private List<Webtoon> genreRecommend(String genre, int radomNumber) {
        if(radomNumber % 2 == 1){
            switch (genre) {
                case "드라마":
                    return webtoonRepository.daumGenreRecommendQuery(genre, radomNumber);
                default:
                    return List.of(new Webtoon());
            }
        } else {
            switch (genre) {
                case "드라마":
                    return webtoonRepository.naverGenreRecommendQuery(genre, radomNumber);
                default:
                    return List.of(new Webtoon());
            }
        }
    }
}
