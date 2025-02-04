package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon

interface WebtoonCustomRepository {
    fun fetchForAdmin(page: Int, pageSize: Int): List<Webtoon>
    fun fetchCountForAdmin(): Long
    fun getGenreRecommendWebtoon(genre: String): Webtoon?
    fun get_Top10_Naver_Webtoons_ByGenre(genre: String): List<Webtoon>
    fun get_Top10_Daum_Webtoons_ByGenre(genre: String): List<Webtoon>
    fun getCompletedWebtoons(lastWebtoonId: Long?, pageSize: Long): List<Webtoon>
}
