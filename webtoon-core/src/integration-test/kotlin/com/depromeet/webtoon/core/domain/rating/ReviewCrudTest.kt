package com.depromeet.webtoon.core.domain.rating

import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.rating.model.Rating
import com.depromeet.webtoon.core.domain.rating.repository.RatingRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.type.WebtoonSite
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
class ReviewCrudTest(
    val ratingRepository: RatingRepository,
    val accountRepository: AccountRepository,
    val webtoonRepository: WebtoonRepository,
    val authorRepository: AuthorRepository
) : FunSpec({

    test("Create Review"){
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L,WebtoonSite.NAVER,"테스트작품", listOf(savedAuthor)))

        val review = Rating(
            null,
            savedWebtoon,
            savedAccount,
            "재밌다",
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now())

        // when
        val savedReview = ratingRepository.save(review)

        // then
        savedReview.id.shouldNotBeNull()
    }


    test("Select Review"){
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L,WebtoonSite.NAVER,"테스트작품", listOf(savedAuthor)))

        val review = Rating(
            null,
            savedWebtoon,
            savedAccount,
            "재밌다",
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now())

        val savedReview = ratingRepository.save(review)

        // when
        ratingRepository.findById(savedReview.id!!).get().shouldNotBeNull()
        ratingRepository.findById(savedReview.id!!).get().comment.shouldBe("재밌다")
    }

    test("Update Review"){
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L,WebtoonSite.NAVER,"테스트작품", listOf(savedAuthor)))

        val review = Rating(
            null,
            savedWebtoon,
            savedAccount,
            "재밌다",
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now())

        val savedReview = ratingRepository.save(review)

        // when
        savedReview.comment = "재미없다"
        ratingRepository.save(savedReview)

        // then
        ratingRepository.findById(savedReview.id!!).get().comment.shouldBe("재미없다")
    }

    test("Delete Review"){
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L,WebtoonSite.NAVER,"테스트작품", listOf(savedAuthor)))

        val review = Rating(
            null,
            savedWebtoon,
            savedAccount,
            "재밌다",
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now())

        val savedReview = ratingRepository.save(review)

        // when
        ratingRepository.deleteAll()

        // then
        ratingRepository.findAll().shouldHaveSize(0)
    }

})
