package com.depromeet.webtoon.core.testsupport

import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.review.model.Review
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import org.springframework.stereotype.Component

@Component
class WebtoonTestDataHelper(
    val webtoonRepository: WebtoonRepository,
    val reviewRepository: ReviewRepository,
    val accountRepository: AccountRepository,
    val authorRepository: AuthorRepository
) {
    fun saveTestWebtoons(webtoons: List<Webtoon>): List<Webtoon> {
        return webtoonRepository.saveAll(webtoons)
    }

    fun saveTestWebtoons(vararg webtoons: Webtoon): List<Webtoon> {
        return this.saveTestWebtoons(webtoons.toList())
    }

    fun saveTestReviews(reviews: List<Review>): List<Review> {
        return reviewRepository.saveAll(reviews)
    }

    fun saveTestReviews(vararg reviews: Review): List<Review> {
        return this.saveTestReviews(reviews.toList())
    }

    fun saveTestAccount(accounts: List<Account>): List<Account> {
        return accountRepository.saveAll(accounts)
    }

    fun saveTestAuthor(authors: List<Author>): List<Author> {
        return authorRepository.saveAll(authors)
    }

    fun saveTestAuthor(vararg authors: Author): List<Author> {
        return authorRepository.saveAll(authors.toList())
    }
}
