package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.core.domain.webtoon.model.QWebtoon.webtoon
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.hibernate.Hibernate
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
class WebtoonCustomRepositoryImpl(entityManger: EntityManager) : WebtoonCustomRepository,
    QuerydslRepositorySupport(Webtoon::class.java) {

    private val query = JPAQueryFactory(entityManger)

    override fun get_Top10_Naver_Webtoons_ByGenre(genre: String): List<Webtoon> {
        return query
            .selectFrom(webtoon)
            .join(webtoon.authors).fetchJoin()
            .where(webtoonGenreEq(genre), webtoon.site.eq(WebtoonSite.NAVER))
            .orderBy(webtoon.score.desc())
            .limit(10)
            .fetch()
    }

    override fun get_Top10_Daum_Webtoons_ByGenre(genre: String): List<Webtoon> {
        return query
            .selectFrom(webtoon)
            .join(webtoon.authors).fetchJoin()
            .where(webtoonGenreEq(genre), webtoon.site.eq(WebtoonSite.DAUM))
            .orderBy(webtoon.score.desc())
            .limit(10)
            .fetch()
    }

    override fun getCompletedWebtoons(lastWebtoonId: Long?, pageSize: Long): List<Webtoon> {
        return query
            .selectFrom(webtoon)
            .join(webtoon.authors).fetchJoin()
            .where(webtoonIdGt(lastWebtoonId), webtoon.isComplete.isTrue)
            .orderBy(webtoon.id.asc())
            .limit(pageSize+1)
            .fetch()
    }

    private fun webtoonIdGt(lastWebtoonId: Long?): Predicate? {
        if(lastWebtoonId != null){
            return webtoon.id.gt(lastWebtoonId)
        }
        return null
    }

    override fun getGenreRecommendWebtoon(genres: String): Webtoon? {
        // score 상위 5개중 한개를 선택할 것
        val random = (Math.random() * 5).toLong()
        val dynamicSite = BooleanBuilder()
        // 두가지 사이트를 섞어서 보여주기 위함
        if (random.toInt() % 2 == 0) {
            dynamicSite.and(webtoon.site.eq(WebtoonSite.NAVER))
        } else (
            dynamicSite.and(webtoon.site.eq(WebtoonSite.DAUM))
            )

        return query
            .selectFrom(webtoon)
            .where(webtoonGenreEq(genres), dynamicSite)
            .orderBy(webtoon.score.desc())
            .offset(random)
            .limit(1)
            .fetchOne()
    }

    private fun webtoonGenreEq(genres: String): Predicate? {
        if (genres == "순정") {
            return (
                webtoon.genres.contains("로맨스")
                    .and(webtoon.genres.contains("스토리"))
                ).or(webtoon.genres.contains("순정"))
        } else {
            return webtoon.genres.contains(genres)
        }
    }

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
        return from(webtoon).fetchCount()
    }
}
