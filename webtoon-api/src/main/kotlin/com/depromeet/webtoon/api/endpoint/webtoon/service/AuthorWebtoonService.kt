package com.depromeet.webtoon.api.endpoint.webtoon.service

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.api.endpoint.webtoon.dto.AuthorWebtoonResponse
import com.depromeet.webtoon.api.endpoint.webtoon.dto.convertToAuthorWebtoonResponse
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.stereotype.Service

@Service
class AuthorWebtoonService(
    val authorRepository: AuthorRepository,
    val webtoonRepository: WebtoonRepository
) {
    fun getAuthorWebtoons(authorId: Long): ApiResponse<AuthorWebtoonResponse> {
        val optionalAuthor = authorRepository.findById(authorId)
        optionalAuthor.orElseThrow { ApiValidationException("잘못된 작가 ID 입니다") }
        val author = optionalAuthor.get()
        val webtoons = webtoonRepository.findByAuthors(author)
        val authorWebtoonResponse = convertToAuthorWebtoonResponse(author.name, webtoons)
        return ApiResponse.ok(authorWebtoonResponse)
    }
}
