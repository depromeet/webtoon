package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WebtoonRepository : JpaRepository<Webtoon, Long> {
    fun findBySiteAndTitle(site: WebtoonSite, title: String): Webtoon?
    fun findAllBySiteAndTitleIn(site: WebtoonSite, titles: List<String>): List<Webtoon>

    // fun findAllByWeekdaysOrderByPopularityAsc(weeks: String): List<Webtoon>?
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
            left outer join webtoon_genre g on w.id=g.webtoon_id
        where( g.genre in (?) )
        and w.score>9
        and w.site = 'DAUM'
        limit ?,1
        """, nativeQuery = true)
    fun daumGenreRecommendQuery(genres: String, randomRow: Int): List<Webtoon>

    @Query(
        """
        select * from webtoon w
            left outer join webtoon_genre g on w.id=g.webtoon_id
        where( g.genre in (?) )
        and w.score>9
        and w.site = 'NAVER'
        limit ?,1
        """, nativeQuery = true)
    fun naverGenreRecommendQuery(genre: String, randomRow: Int): List<Webtoon>


    fun findTop5ByGenresInAndScoreGreaterThanAndSite(genres: List<String>, score: Double, site: WebtoonSite): List<Webtoon>
}
