package com.depromeet.webtoon.api.endpoint.author.service

import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AuthorRecommendServiceTest:FunSpec ({

    lateinit var authorRepository: AuthorRepository
    lateinit var authorRecommendService: AuthorRecommendService

    beforeTest {
        authorRepository = mockk()
        authorRecommendService = AuthorRecommendService(authorRepository)
    }

    context("AuthorRecommendServiceTest"){
        test("getRecommendAuthors") {

            // given
            every { authorRepository.find20RandomAuthors() } returns listOf(authorFixture(1L, "작가1"), authorFixture(2L, "작가2"))

            // when
            val recommendAuthors = authorRecommendService.getRecommendAuthors()

            verify(exactly = 1) { authorRepository.find20RandomAuthors() }
            recommendAuthors.authors.size.shouldBe(2)
        }
    }

})
