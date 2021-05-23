package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.hibernate.Hibernate
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
class WebtoonCustomRepositoryImpl(val entityManager: EntityManager) : WebtoonCustomRepository {

    override fun findAllForAdmin(page: Int, pageSize: Int): List<Webtoon> {
        return entityManager.createQuery("SELECT w FROM Webtoon AS w ORDER BY w.id DESC", Webtoon::class.java)
            .setMaxResults(pageSize)
            .setFirstResult((page - 1) * pageSize)
            .resultList
            .onEach {
                Hibernate.initialize(it.authors)
                Hibernate.initialize(it.genres)
            }
    }
}
