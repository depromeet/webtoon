package com.depromeet.webtoon.api.endpoint.comment

import com.depromeet.webtoon.api.endpoint.comment.dto.CommentRequest
import com.depromeet.webtoon.api.endpoint.comment.service.CommentImportService
import com.depromeet.webtoon.api.endpoint.comment.service.CommentService
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.DeleteMapping
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

    @PostMapping("/post")
    fun upsertComment(@RequestBody commentRequest: CommentRequest): ApiResponse<String> {

        commentImportService.upsertComment(commentRequest)

        return ApiResponse.ok("todo")
    }

    @DeleteMapping("")
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "댓글id", required = true)
    )
    fun deleteComment(@RequestParam id: Long): ApiResponse<String> {
        // todo 권한 확인을 위한 데이터 전달
        commentService.deleteComment(id)
        return ApiResponse.ok("정상적으로 삭제되었습니다.")
    }
}
