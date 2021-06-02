package com.depromeet.webtoon.admin.api.webtoon

import com.depromeet.webtoon.core.application.admin.common.AdminListFetchResult
import com.depromeet.webtoon.core.application.admin.webtoon.WebtoonAdminService
import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/webtoons")
class WebtoonAdminController(
    val webtoonAdminService: WebtoonAdminService
) {
    @Transactional(readOnly = true)
    @GetMapping("")
    fun webtoonList(
        @RequestParam(required = false, defaultValue = "1") page: Int?,
        @RequestParam(required = false, defaultValue = "25") pageSize: Int?,
        httpServletResponse: HttpServletResponse
    ): AdminListFetchResult<WebtoonResponse> {
        return webtoonAdminService.fetchWebtoons(page!!, pageSize!!)
    }
}
