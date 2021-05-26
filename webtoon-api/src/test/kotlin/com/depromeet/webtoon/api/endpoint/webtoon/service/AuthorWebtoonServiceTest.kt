package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AuthorWebtoonServiceTest: FunSpec({

    lateinit var authorRepository: AuthorRepository
    lateinit var webtoonRepository: WebtoonRepository
    lateinit var authorWebtoonService: AuthorWebtoonService

    beforeTest {
        authorRepository = mockk()
        webtoonRepository = mockk()
        authorWebtoonService = AuthorWebtoonService(authorRepository, webtoonRepository)
    }

    context("AuthorWebtoonService"){
        test("getAuthorWebtoons"){

            // given
            val author = authorFixture(id= 1L, name = "test")
            every { authorRepository.findByName(any()) } returns author
            every { webtoonRepository.findByAuthors(any()) } returns listOf(webtoonFixture(id= 1L, authors = listOf(
                author)))

            // when
            authorWebtoonService.getAuthorWebtoons("test")

            // then
            verify(exactly = 1) { authorRepository.findByName(author.name) }
            verify(exactly = 1) { webtoonRepository.findByAuthors(author)  }

        }
    }
})
