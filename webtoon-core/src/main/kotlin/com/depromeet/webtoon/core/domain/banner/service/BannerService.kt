package com.depromeet.webtoon.core.domain.banner.service

import com.depromeet.webtoon.core.domain.banner.dto.BannerCreateRequest
import com.depromeet.webtoon.core.domain.banner.dto.SearchBannerRequest
import com.depromeet.webtoon.core.domain.banner.model.Banner
import com.depromeet.webtoon.core.domain.banner.repository.BannerRepository
import org.springframework.stereotype.Service

@Service
class BannerService(val bannerRepository: BannerRepository) {

    // 배너를 생성합니다
    fun createBanner(createRequest: BannerCreateRequest): Banner {
        return with(createRequest) {
            Banner(
                bannerType = bannerType,
                caption = caption,
                webtoon = webtoon,
                priority = priority,
                displayBeginDateTime = displayBeginDateTime,
                displayEndDateTime = displayEndDateTime
            )
        }.let { bannerRepository.save(it) }
    }

    fun getBanner(id: Long): Banner {
        return bannerRepository.findById(id)
            .orElseThrow { IllegalArgumentException("매칭되는 banner를 찾지 못함 id: $id") }
    }

    fun searchBanners(request: SearchBannerRequest): List<Banner> {
        return bannerRepository.searchByTypeAndDateTime(request.bannerType, request.baseDateTime)
    }
}
