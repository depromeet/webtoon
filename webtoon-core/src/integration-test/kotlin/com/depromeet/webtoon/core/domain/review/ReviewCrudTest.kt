package com.depromeet.webtoon.core.domain.review

import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.review.model.Review
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.type.WebtoonSite
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
class ReviewCrudTest(
    val reviewRepository: ReviewRepository,
    val accountRepository: AccountRepository,
    val webtoonRepository: WebtoonRepository,
    val authorRepository: AuthorRepository
) : FunSpec({

    test("Create Review"){
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L,WebtoonSite.NAVER,"테스트작품", listOf(savedAuthor)))

        val review = Review(
            null,
            savedWebtoon,
            savedAccount,
            "재밌다",
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now())

        // when
        val savedReview = reviewRepository.save(review)

        // then
        savedReview.id.shouldNotBeNull()
    }


    test("Select Review"){
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L,WebtoonSite.NAVER,"테스트작품", listOf(savedAuthor)))

        val review = Review(
            null,
            savedWebtoon,
            savedAccount,
            "재밌다",
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now())

        val savedReview = reviewRepository.save(review)

        // when
        reviewRepository.findById(savedReview.id!!).get().shouldNotBeNull()
        reviewRepository.findById(savedReview.id!!).get().comment.shouldBe("재밌다")
    }

    test("Update Review"){
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L,WebtoonSite.NAVER,"테스트작품", listOf(savedAuthor)))

        val review = Review(
            null,
            savedWebtoon,
            savedAccount,
            "재밌다",
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now())

        val savedReview = reviewRepository.save(review)

        // when
        savedReview.comment = "재미없다"
        reviewRepository.save(savedReview)

        // then
        reviewRepository.findById(savedReview.id!!).get().comment.shouldBe("재미없다")
    }

    test("Delete Review"){
        // given
        val savedAccount = accountRepository.save(accountFixture())
        val savedAuthor = authorRepository.save(authorFixture())
        val savedWebtoon = webtoonRepository.save(webtoonFixture(1L,WebtoonSite.NAVER,"테스트작품", listOf(savedAuthor)))

        val review = Review(
            null,
            savedWebtoon,
            savedAccount,
            "재밌다",
            3.0,
            5.0,
            LocalDateTime.now(),
            LocalDateTime.now())

        val savedReview = reviewRepository.save(review)

        // when
        reviewRepository.deleteAll()

        // then
        reviewRepository.findAll().shouldHaveSize(0)
    }

})
