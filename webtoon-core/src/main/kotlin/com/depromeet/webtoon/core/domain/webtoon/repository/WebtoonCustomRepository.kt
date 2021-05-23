package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon

interface WebtoonCustomRepository {
    fun findAllForAdmin(page: Int, pageSize: Int): List<Webtoon>
}
