package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.stereotype.Service

@Service
class AuthorWebtoonService(
    val authorRepository: AuthorRepository,
    val webtoonRepository: WebtoonRepository
) {
    fun getAuthorWebtoons(authorId: Long): List<Webtoon> {

        val optionalAuthor = authorRepository.findById(authorId)
        optionalAuthor.orElseThrow { ApiValidationException("잘못된 작가 ID 입니다") }

        return webtoonRepository.findByAuthors(optionalAuthor.get())
    }
}
