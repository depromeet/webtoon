package com.depromeet.webtoon.api.endpoint.dto

import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.domain.review.dto.ScoreDto
import com.depromeet.webtoon.core.domain.review.model.Review
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WeekDay

data class WebtoonDetailResponse(
    var id: Long,
    var title: String,
    var thumbnail: String,
    var url: String,
    var authors: List<AuthorResponse>,
    var site: WebtoonSite,
    var weekday: List<WeekDay>,
    var summary: String,
    val score: ScoreResponse,
    val comments: List<String>
)

fun convertToWebtoonDetailResponse(webtoon: Webtoon, scores: ScoreDto, comments: List<String>):WebtoonDetailResponse {
    return WebtoonDetailResponse(
        webtoon.id!!,
        webtoon.title,
        webtoon.thumbnail,
        webtoon.url,
        webtoon.authors.map { AuthorResponse(it.id!!, it.name) },
        webtoon.site,
        webtoon.weekdays,
        webtoon.summary,
        ScoreResponse(scores.storyScore!!, scores.drawingScore!!),
        comments
    )

}
