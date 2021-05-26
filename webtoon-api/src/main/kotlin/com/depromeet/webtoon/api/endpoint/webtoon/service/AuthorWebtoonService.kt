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
    val webtoonRepository: WebtoonRepository)
{
    fun getAuthorWebtoons(authorName: String): ApiResponse<AuthorWebtoonResponse> {
        val optionalAuthor = authorRepository.findByName(authorName) ?: throw ApiValidationException("잘못된 작가명 입니다.")
        val webtoons = webtoonRepository.findByAuthors(optionalAuthor)
        val authorWebtoonResponse = convertToAuthorWebtoonResponse(authorName, webtoons)
        return ApiResponse.ok(authorWebtoonResponse)

    }
}
