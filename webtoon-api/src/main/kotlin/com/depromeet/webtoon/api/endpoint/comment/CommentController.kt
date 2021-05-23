package com.depromeet.webtoon.api.endpoint.comment

import com.depromeet.webtoon.api.endpoint.comment.dto.CommentRequest
import com.depromeet.webtoon.api.endpoint.comment.service.CommentImportService
import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Api("commentController")
@RestController("/api/v1/comment")
class CommentController(
    val commentImportService: CommentImportService
) {

    @PostMapping("/post")
    fun upsertComment(@RequestBody commentRequest: CommentRequest): ApiResponse<String> {

        commentImportService.upsertComment(commentRequest)

        return ApiResponse.ok("todo")
    }

    /*@GetMapping("/lists")
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "웹툰 id", required = true)
    )
    fun getComments(@RequestParam id: Long):  {

    }*/

}
