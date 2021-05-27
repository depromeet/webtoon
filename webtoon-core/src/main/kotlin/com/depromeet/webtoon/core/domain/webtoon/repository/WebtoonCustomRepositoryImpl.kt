package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.core.domain.webtoon.model.QWebtoon
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.hibernate.Hibernate
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.transaction.annotation.Transactional

@Transactional
class WebtoonCustomRepositoryImpl() : WebtoonCustomRepository, QuerydslRepositorySupport(Webtoon::class.java) {

    override fun fetchForAdmin(page: Int, pageSize: Int): List<Webtoon> {
        return entityManager!!.createQuery("SELECT w FROM Webtoon AS w ORDER BY w.id DESC", Webtoon::class.java)
            .setMaxResults(pageSize)
            .setFirstResult((page - 1) * pageSize)
            .resultList
            .onEach {
                Hibernate.initialize(it.authors)
                Hibernate.initialize(it.genres)
            }
    }

    override fun fetchCountForAdmin(): Long {
        return from(QWebtoon.webtoon).fetchCount()
    }
}
