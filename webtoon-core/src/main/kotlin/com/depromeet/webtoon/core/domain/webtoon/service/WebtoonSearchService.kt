package com.depromeet.webtoon.core.domain.webtoon.service

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WebtoonSearchService(val webtoonRepository: WebtoonRepository) {
    @Transactional(readOnly = true)
    fun searchByQueryStr(text: String): List<Webtoon> {
        return webtoonRepository.searchByQuery(text)
    }
}
