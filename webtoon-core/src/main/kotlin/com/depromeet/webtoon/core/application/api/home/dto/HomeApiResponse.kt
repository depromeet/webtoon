package com.depromeet.webtoon.core.application.api.home.dto

import com.depromeet.webtoon.core.application.api.dto.AuthorResponse
import com.depromeet.webtoon.core.application.api.dto.WebtoonResponse
import com.depromeet.webtoon.core.application.common.dto.BannerResponse
import com.depromeet.webtoon.core.domain.author.dto.AuthorRecommendResponse
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("홈화면 반환 데이터")
data class HomeApiResponse(
    @ApiModelProperty(value = "상단 배너")
    val mainBanner: List<BannerResponse>,
    @ApiModelProperty(value = "요일별 웹툰 리스트")
    val weekdayWebtoons: List<WebtoonResponse>,
    @ApiModelProperty(value = "인기급상승 웹툰 리스트")
    val trendingWebtoons: List<WebtoonResponse>,
    @ApiModelProperty(value = "장르별 웹툰 리스트")
    val genreWebtoons: List<WebtoonResponse>,
    @ApiModelProperty(value = "몰아보기 좋은 웹툰 리스트")
    val bingeWatchableWebtoons: List<WebtoonResponse>,
    @ApiModelProperty(value = "추천 작가 리스트")
    val recommendAuthors: List<AuthorResponse>
)
