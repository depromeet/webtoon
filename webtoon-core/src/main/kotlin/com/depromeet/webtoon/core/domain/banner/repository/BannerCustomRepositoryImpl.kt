package com.depromeet.webtoon.core.domain.banner.repository

import com.depromeet.webtoon.core.domain.banner.model.Banner
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
class BannerCustomRepositoryImpl(val entityManager: EntityManager) : BannerCustomRepository {

    override fun fetchForAdmin(page: Int, pageSize: Int): List<Banner> {
        val jpql = """
            SELECT b
              FROM Banner AS b
              JOIN FETCH b.webtoon
          ORDER BY b.id DESC
        """.trimIndent()

        return entityManager.createQuery(jpql, Banner::class.java)
            .setMaxResults(pageSize)
            .setFirstResult((page - 1) * pageSize)
            .resultList
    }

    override fun fetchCountForAdmin(): Long {
        val jpql = """
            SELECT count(b)
              FROM Banner AS b
        """.trimIndent()

        return entityManager.createQuery(jpql, Long::class.javaObjectType)
            .singleResult
    }
}
