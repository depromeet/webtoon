package com.depromeet.webtoon.core.domain.webtoon.service

import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.domain.webtoon.dto.CompleteWebtoonResponse
import com.depromeet.webtoon.core.domain.webtoon.dto.convertToCompleteWebtoonResponse
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import org.springframework.stereotype.Service

@Service
class CompleteWebtoonService (
    val webtoonRepository: WebtoonRepository
        ) {
    fun getCompleteWebtoons(lastWebtoonId: Long?, pageSize: Long): CompleteWebtoonResponse {
        return webtoonRepository
            .getCompletedWebtoons(lastWebtoonId, pageSize)
            .convertToWebtoonResponses()
            .convertToCompleteWebtoonResponse()
    }
}
