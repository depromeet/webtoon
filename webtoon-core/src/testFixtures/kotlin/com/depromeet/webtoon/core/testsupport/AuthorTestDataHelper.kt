package com.depromeet.webtoon.core.testsupport

import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import org.springframework.stereotype.Component

@Component
class AuthorTestDataHelper(
    authorRepository: AuthorRepository
) {
    init {
        AuthorTestDataHelper.authorRepository = authorRepository
    }

    companion object {
        lateinit var authorRepository: AuthorRepository

        fun List<Author>.saveAll(): List<Author> {
            return authorRepository.saveAll(this)
        }

        fun Author.save(): Author {
            return authorRepository.save(this)
        }
    }
}
