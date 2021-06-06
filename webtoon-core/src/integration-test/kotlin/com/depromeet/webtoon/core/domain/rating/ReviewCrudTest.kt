package com.depromeet.webtoon.core.domain.rating

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.rating.model.Rating
import com.depromeet.webtoon.core.domain.rating.repository.RatingRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@DataJpaTest
class ReviewCrudTest(
    val ratingRepository: RatingRepository,
    val accountRepository: AccountRepository,
    val webtoonRepository: WebtoonRepository,
    val authorRepository: AuthorRepository
) : FunSpec({

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
