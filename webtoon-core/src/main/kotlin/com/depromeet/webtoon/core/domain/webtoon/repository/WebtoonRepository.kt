package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WebtoonRepository : JpaRepository<Webtoon, Long>, WebtoonCustomRepository {

    fun findBySiteAndTitle(site: WebtoonSite, title: String): Webtoon?

    @EntityGraph(attributePaths = ["authors"])
    fun findByAuthors(author: Author): List<Webtoon>

    @EntityGraph(attributePaths = ["authors"])
    fun findAllByWeekdaysOrderByPopularityAsc(weekDay: WeekDay): List<Webtoon>

    // 망하기 딱 좋게 생김. lucene 으로 변경 필요.
    @Query(
        """select w
              from Webtoon w
              join fetch w.authors a
             where w.title like concat('%',:text,'%')
                or a.name like concat('%', :text, '%')
          order by w.id desc"""
    )
    fun searchByQuery(text: String): List<Webtoon>

    @Query(
        """
            select * from webtoon w
            order by rand()
            limit 3
        """,
        nativeQuery = true
    )
    fun findRandomWebtoons(): List<Webtoon>

    @Query(
        """
            select * from webtoon w
            order by rand()
            limit 20
        """,
        nativeQuery = true
    )
    fun find20RandomWebtoons(): List<Webtoon>
}
