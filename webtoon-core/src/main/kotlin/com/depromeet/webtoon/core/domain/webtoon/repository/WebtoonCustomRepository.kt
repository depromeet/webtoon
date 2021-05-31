package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon

interface WebtoonCustomRepository {
    fun fetchForAdmin(page: Int, pageSize: Int): List<Webtoon>
    fun fetchCountForAdmin(): Long
    fun genreRecommendWebtoon(random: Long, genre: String): Webtoon?
    fun get_Top10_Naver_Webtoons_ByGenre(genre: String): List<Webtoon>
    fun get_Top10_Daum_Webtoons_ByGenre(genre: String): List<Webtoon>

}
