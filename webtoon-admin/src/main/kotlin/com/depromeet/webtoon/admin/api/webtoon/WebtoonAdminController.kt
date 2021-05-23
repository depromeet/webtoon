package com.depromeet.webtoon.admin.api.webtoon

import com.depromeet.webtoon.admin.api.common.AdminListApiResponse
import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import com.depromeet.webtoon.core.application.api.dto.convertToWebtoonResponses
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/webtoons")
class WebtoonAdminController(
    val webtoonRepository: WebtoonRepository
) {
    @Transactional(readOnly = true)
    @GetMapping("")
    fun webtoonList(
        @RequestParam(required = false, defaultValue = "0") page: Int?,
        @RequestParam(required = false, defaultValue = "25") pageSize: Int?,
        httpServletResponse: HttpServletResponse
    ): AdminListApiResponse<WebtoonResponse> {
        val webtoons =
            webtoonRepository.findAllForAdmin(page!!, pageSize!!).convertToWebtoonResponses()

        return AdminListApiResponse(1000, webtoons)
    }
}
