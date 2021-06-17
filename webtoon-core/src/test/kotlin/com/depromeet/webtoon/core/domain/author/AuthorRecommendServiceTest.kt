package com.depromeet.webtoon.core.domain.author

import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.author.service.AuthorRecommendService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
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
        test("getRecommendAuthors 작가 프로필 랜덤 이미지 생성확인") {

            // given
            every { authorRepository.find20RandomAuthors() } returns listOf(authorFixture(1L, "작가1"), authorFixture(2L, "작가2"))

            // when
            val recommendAuthors = authorRecommendService.getRecommendAuthors()


            // then
            verify(exactly = 1) { authorRepository.find20RandomAuthors() }
            recommendAuthors.authors.size.shouldBe(2)
            recommendAuthors.authors.stream().forEach { it.authorImage.shouldNotBeNull() }
        }

        test("getHomeApiRecommendAuthors 작가 프로필 랜덤 이미지 생성확인") {
            // given
            every { authorRepository.find20RandomAuthors() } returns listOf(authorFixture(1L, "작가1"), authorFixture(2L, "작가2"))

            // when
            val recommendAuthors = authorRecommendService.getHomeApiRecommendAuthors()

            // then
            verify(exactly = 1) { authorRepository.find20RandomAuthors()}
            recommendAuthors.size.shouldBe(2)
            recommendAuthors.stream().forEach { it.authorImage.shouldNotBeNull() }
            }
    }

})
