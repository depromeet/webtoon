package com.depromeet.webtoon.core.domain.rating.repository

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.rating.model.Rating
import com.depromeet.webtoon.core.domain.rating.ratingFixture
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@SpringBootTest
class RatingRepositoryTest(
    val ratingRepository: RatingRepository,
    val accountRepository: AccountRepository,
    val webtoonRepository: WebtoonRepository,
    val authorRepository: AuthorRepository
) : FunSpec({

    test("findByWebtoonIdAndAccountId"){
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(authors = listOf(savedAuthor)))
        ratingRepository.save(ratingFixture(account = savedAccount, webtoon = savedWebtoon))

        // when
        val findRating = ratingRepository.findByWebtoonIdAndAccountId(savedWebtoon.id!!, savedAccount.id!!)

        // then
        findRating.shouldNotBeNull()
        findRating.account.id.shouldBe(savedAccount.id)
        findRating.webtoon.id.shouldBe(savedWebtoon.id)
        findRating.drawingScore.shouldBe(ratingFixture().drawingScore)
        findRating.storyScore.shouldBe(ratingFixture().storyScore)
    }

    test("getAvgDrawingScore"){
        // given
        val account1 = accountRepository.save(accountFixture())
        val account2 = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(authors = listOf(savedAuthor)))
        val rating1 = ratingFixture(account = account1, webtoon = savedWebtoon, drawingScore = 4.0)
        val rating2 = ratingFixture(account = account2, webtoon = savedWebtoon, drawingScore = 6.0)
        ratingRepository.save(rating1)
        ratingRepository.save(rating2)

        //when
        val drawingScoreAvg = ratingRepository.getAvgDrawingScore(savedWebtoon)

        //then
        drawingScoreAvg.shouldBe(rating1.drawingScore!!.plus(rating2.drawingScore!!).div(2))
    }

    test("getAvgStoryScore"){
        // given
        val account1 = accountRepository.save(accountFixture())
        val account2 = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(authors = listOf(savedAuthor)))
        val rating1 = ratingFixture(account = account1, webtoon = savedWebtoon, storyScore = 4.0)
        val rating2 = ratingFixture(account = account2, webtoon = savedWebtoon, storyScore = 6.0)
        ratingRepository.save(rating1)
        ratingRepository.save(rating2)

        // when
        val storyScoreAvg = ratingRepository.getAvgStoryScore(savedWebtoon)

        // then
        storyScoreAvg.shouldBe(rating1.storyScore!!.plus(rating2.storyScore!!).div(2))

    }



    test("Create Review") {
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L, WebtoonSite.NAVER, "테스트작품", listOf(savedAuthor)))

        val review = Rating(
            null,
            savedWebtoon,
            savedAccount,
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // when
        val savedReview = ratingRepository.save(review)

        // then
        savedReview.id.shouldNotBeNull()
    }

    test("Select Review") {
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L, WebtoonSite.NAVER, "테스트작품", listOf(savedAuthor)))

        val review = Rating(
            null,
            savedWebtoon,
            savedAccount,
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        val savedReview = ratingRepository.save(review)

        // when
        ratingRepository.findById(savedReview.id!!).get().shouldNotBeNull()
    }

    test("Update Review") {
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L, WebtoonSite.NAVER, "테스트작품", listOf(savedAuthor)))

        val review = Rating(
            null,
            savedWebtoon,
            savedAccount,
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        val savedReview = ratingRepository.save(review)

        // when
        ratingRepository.save(savedReview)
    }

    test("Delete Review") {
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L, WebtoonSite.NAVER, "테스트작품", listOf(savedAuthor)))

        val review = Rating(
            null,
            savedWebtoon,
            savedAccount,
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        val savedReview = ratingRepository.save(review)

        // when
        ratingRepository.deleteAll()

        // then
        ratingRepository.findAll().shouldHaveSize(0)
    }
})
