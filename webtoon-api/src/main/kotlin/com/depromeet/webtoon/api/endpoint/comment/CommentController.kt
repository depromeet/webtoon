package com.depromeet.webtoon.api.endpoint.comment

import com.depromeet.webtoon.api.endpoint.comment.dto.CommentRequest
import com.depromeet.webtoon.api.endpoint.comment.dto.CommentsResponse
import com.depromeet.webtoon.api.endpoint.comment.service.CommentImportService
import com.depromeet.webtoon.api.endpoint.comment.service.CommentService
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api("commentController")
@RestController
@RequestMapping("/api/v1/comment")
class CommentController(
    val commentImportService: CommentImportService,
    val commentService: CommentService,
) {

    @GetMapping("/list")
    @ApiImplicitParams(
        ApiImplicitParam(name = "webtoonId", value = "웹툰id", required = true),
        ApiImplicitParam(name = "commentId", value = "댓글id", required = false),
        ApiImplicitParam(name = "pageSize", value = "페이지 크기", required = true),
        ApiImplicitParam(
            name = "Authorization",
            value = "authorization header",
            required = true,
            dataType = "string",
            paramType = "header",
            defaultValue = "Bearer testToken"
        )
    )
    fun getComments(@RequestParam webtoonId: Long, commentId: Long?, pageSize: Long): ApiResponse<CommentsResponse> {
        return commentService.getComments(webtoonId, commentId, pageSize)
    }

    @PostMapping("/post")
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
    fun upsertComment(@RequestBody commentRequest: CommentRequest): ApiResponse<String> {

        commentImportService.upsertComment(commentRequest)

        return ApiResponse.ok("todo")
    }

    @DeleteMapping("")
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "댓글id", required = true),
        ApiImplicitParam(
            name = "Authorization",
            value = "authorization header",
            required = true,
            dataType = "string",
            paramType = "header",
            defaultValue = "Bearer testToken"
        )
    )
    fun deleteComment(@RequestParam id: Long): ApiResponse<String> {
        // todo 권한 확인을 위한 데이터 전달
        commentService.deleteComment(id)
        return ApiResponse.ok("정상적으로 삭제되었습니다.")
    }
}
