package com.depromeet.webtoon.api.common.annotation

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams

@Retention(AnnotationRetention.RUNTIME)
@ApiImplicitParams(
    ApiImplicitParam(name = "webtoonId", value = "웹툰id", required = true),
    ApiImplicitParam(name = "lastCommentId", value = "마지막 댓글id (마지막 댓글 이후의 댓글 목록을 가져옵니다.)", required = false),
    ApiImplicitParam(name = "pageSize", value = "페이지 크기", defaultValue = "20",),
    ApiImplicitParam(
        name = "Authorization",
        value = "authorization header",
        required = true,
        dataType = "string",
        paramType = "header",
        defaultValue = "Bearer testToken"
    )
)
annotation class SwaggerGetComments()
