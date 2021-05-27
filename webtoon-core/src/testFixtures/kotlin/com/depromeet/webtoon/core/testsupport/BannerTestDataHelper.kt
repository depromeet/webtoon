package com.depromeet.webtoon.core.testsupport

import com.depromeet.webtoon.core.domain.banner.model.Banner
import com.depromeet.webtoon.core.domain.banner.repository.BannerRepository
import org.springframework.stereotype.Component

@Component
class BannerTestDataHelper(
    bannerRepository: BannerRepository
) {
    init {
        BannerTestDataHelper.bannerRepository = bannerRepository
    }

    companion object {
        lateinit var bannerRepository: BannerRepository

        fun List<Banner>.saveAll(): List<Banner> {
            return bannerRepository.saveAll(this)
        }

        fun Banner.save(): Banner {
            return bannerRepository.save(this)
        }
    }
}
