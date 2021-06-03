package com.depromeet.webtoon.api.endpoint.comment

import com.depromeet.webtoon.api.endpoint.comment.dto.CommentDeleteRequest
import com.depromeet.webtoon.api.endpoint.comment.dto.CommentRequest
import com.depromeet.webtoon.api.endpoint.comment.dto.CommentsResponse
import com.depromeet.webtoon.api.endpoint.comment.service.CommentImportService
import com.depromeet.webtoon.api.endpoint.comment.service.CommentService
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.exceptions.ApiValidationException
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
import java.security.Principal

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

    fun upsertComment(
        @RequestBody commentRequest: CommentRequest, principal: Principal
    ): ApiResponse<String> {
        if (commentRequest.accountId.toString() != principal.name) {
            throw ApiValidationException("작성자(수정자)ID 가 잘못되었습니다.")
        }

        commentImportService.upsertComment(commentRequest)
        return ApiResponse.ok("댓글이 정상적으로 작성되었습니다.")
    }

    @DeleteMapping("")
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
    fun deleteComment(@RequestBody request: CommentDeleteRequest, principal: Principal): ApiResponse<String> {
        if(request.accountId.toString() != principal.name){
            throw ApiValidationException("댓글 삭제 권한이 없습니다.")
        }
        commentService.deleteComment(request.commentId)
        return ApiResponse.ok("정상적으로 삭제되었습니다.")
    }
}
