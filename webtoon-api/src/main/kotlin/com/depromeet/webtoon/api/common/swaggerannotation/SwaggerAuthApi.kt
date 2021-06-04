package com.depromeet.webtoon.api.common.swaggerannotation

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams

@Retention(AnnotationRetention.RUNTIME)
@ApiImplicitParams(
    ApiImplicitParam(
        name = "Authorization",
        value = "authorization header",
        required = true,
        dataType = "string",
        paramType = "header",
        defaultValue = "Bearer testToken"
    )
)
annotation class SwaggerAuthApi()
