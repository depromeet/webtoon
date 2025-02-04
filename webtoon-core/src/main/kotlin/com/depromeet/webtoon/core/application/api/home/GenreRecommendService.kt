package com.depromeet.webtoon.core.application.api.home

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import org.springframework.stereotype.Service

@Service
class GenreRecommendService (
    val webtoonRepository: WebtoonRepository
        ) {

    fun getRecommendWebtoonByGenre(): List<Webtoon>{

        val genreRecommendWebtoons = mutableListOf<Webtoon>()

        GENRE.map {
            val optionalRecommendWebtoon = webtoonRepository.getGenreRecommendWebtoon(it)
            if(optionalRecommendWebtoon != null){
                genreRecommendWebtoons.add(optionalRecommendWebtoon)
            }
        }

        return genreRecommendWebtoons
    }

    companion object {
        private  val GENRE = listOf("드라마", "일상", "판타지", "액션", "순정")
    }

}
