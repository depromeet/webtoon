package com.depromeet.webtoon.api.common.swaggerannotation

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams

@Retention(AnnotationRetention.RUNTIME)
@ApiImplicitParams(
    ApiImplicitParam(name = "webtoonId", value = "웹툰id", required = true),
    // todo Offset commentId 처럼 명시적으로 보여줄 것
    ApiImplicitParam(name = "offsetCommentId", value = "offset 댓글id", required = false),
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
