package com.depromeet.webtoon.core.domain.banner.repository

import com.depromeet.webtoon.core.domain.banner.model.Banner
import com.depromeet.webtoon.core.domain.banner.model.BannerInventory
import org.intellij.lang.annotations.Language
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface BannerRepository : JpaRepository<Banner, Long>, BannerCustomRepository {

    @Query(SEARCH_BANNER_JPQL)
    fun searchByTypeAndDateTime(bannerInventory: BannerInventory, baseDateTime: LocalDateTime): List<Banner>

    private companion object {
        @Language("JPAQL")
        const val SEARCH_BANNER_JPQL = """
            SELECT b
              FROM Banner b
              JOIN FETCH b.webtoon
             WHERE b.bannerInventory = :bannerInventory
               AND :baseDateTime BETWEEN b.displayBeginDateTime AND b.displayEndDateTime
          ORDER BY b.priority ASC, b.id DESC
      """
    }
}
