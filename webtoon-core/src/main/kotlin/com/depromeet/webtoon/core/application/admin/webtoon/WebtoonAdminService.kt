package com.depromeet.webtoon.core.application.admin.webtoon

import com.depromeet.webtoon.core.application.admin.common.AdminListFetchResult
import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
import org.springframework.stereotype.Service

@Service
class WebtoonAdminService(
    val webtoonService: WebtoonService
) {

    fun fetchWebtoons(page: Int, pageSize: Int): AdminListFetchResult<WebtoonResponse> {
        val webtoonRepository = webtoonService.webtoonRepository
        val result = webtoonRepository.fetchForAdmin(page, pageSize).convertToWebtoonResponses()
        val total = webtoonRepository.fetchCountForAdmin()

        return AdminListFetchResult(result, total)
    }
}
