package com.depromeet.webtoon.core.domain.author.service

import com.depromeet.webtoon.core.application.api.dto.AuthorResponse
import com.depromeet.webtoon.core.application.api.dto.convertToAuthorResponses
import com.depromeet.webtoon.core.domain.author.dto.AuthorRecommendResponse
import com.depromeet.webtoon.core.domain.author.dto.convertToAuthorRecommendResponse
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import org.springframework.stereotype.Service

@Service
class AuthorRecommendService (val authorRepository: AuthorRepository){

    fun getRecommendAuthors(): AuthorRecommendResponse {
        val authorList = authorRepository.find20RandomAuthors()
        return authorList.convertToAuthorResponses().convertToAuthorRecommendResponse()
    }

    fun getHomeApiRecommendAuthors(): List<AuthorResponse>{
        val authorList = authorRepository.find20RandomAuthors()
        return authorList.convertToAuthorResponses()
    }
}
