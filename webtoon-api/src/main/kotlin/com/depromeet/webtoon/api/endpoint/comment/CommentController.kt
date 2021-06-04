package com.depromeet.webtoon.api.endpoint.comment

import com.depromeet.webtoon.api.common.swaggerannotation.SwaggerAuthApi
import com.depromeet.webtoon.api.common.swaggerannotation.SwaggerGetComments
import com.depromeet.webtoon.api.endpoint.comment.dto.CommentsResponse
import com.depromeet.webtoon.api.endpoint.comment.dto.CreateCommentRequest
import com.depromeet.webtoon.api.endpoint.comment.dto.UpdateCommentRequest
import com.depromeet.webtoon.api.endpoint.comment.service.CommentImportService
import com.depromeet.webtoon.api.endpoint.comment.service.CommentService
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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
    @SwaggerGetComments
    fun getComments(@RequestParam webtoonId: Long, commentId: Long?, pageSize: Long): ApiResponse<CommentsResponse> {
        return commentService.getComments(webtoonId, commentId, pageSize)
    }

    @PostMapping
    @SwaggerAuthApi
    fun createComment(@RequestBody createRequest: CreateCommentRequest): ApiResponse<String> {
        commentImportService.createComment(createRequest)
        return ApiResponse.ok("정상적으로 댓글이 작성되었습니다.")
    }

    @PatchMapping
    @SwaggerAuthApi
    fun updateComment(@RequestBody updateRequest: UpdateCommentRequest): ApiResponse<String>{
        commentImportService.updateComment(updateRequest)
        return ApiResponse.ok("정상적으로 댓글이 수정되었습니다.")
    }

    @DeleteMapping
    @SwaggerAuthApi
    fun deleteComment(@RequestParam commentId: Long): ApiResponse<String> {
       commentImportService.deleteComment(commentId)
        return ApiResponse.ok("정상적으로 댓글이 삭제되었습니다.")
    }
}
