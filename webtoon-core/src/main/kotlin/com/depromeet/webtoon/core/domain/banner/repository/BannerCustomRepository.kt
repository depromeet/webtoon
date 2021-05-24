package com.depromeet.webtoon.core.domain.banner.repository

import com.depromeet.webtoon.core.domain.banner.model.Banner

interface BannerCustomRepository {
    fun fetchForAdmin(page: Int, pageSize: Int): List<Banner>
    fun fetchCountForAdmin(): Long
}
