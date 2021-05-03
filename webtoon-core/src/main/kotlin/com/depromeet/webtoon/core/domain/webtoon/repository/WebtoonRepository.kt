package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.DayOfWeek

@Repository
interface WebtoonRepository : JpaRepository<Webtoon, Long> {
    fun findBySiteAndTitle(site: WebtoonSite, title: String): Webtoon?

    // fun findAllByWeekdaysOrderByPopularityAsc(weeks: String): List<Webtoon>?
    fun findAllByWeekdaysOrderByPopularityAsc(weekDay: WeekDay): List<Webtoon>
}
