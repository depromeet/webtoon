package com.depromeet.webtoon.api.webtoon

import com.depromeet.webtoon.api.endpoint.comment.dto.CommentResponse
import com.depromeet.webtoon.api.endpoint.comment.dto.CommentsResponse
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.dto.convertToAccountResponse
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.comment.commentFixture
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest(
    var mockMvc: MockMvc,
    var webtoonRepository: WebtoonRepository,
    var commentRepository: CommentRepository,
    var accountRepository: AccountRepository,
    var authorRepository: AuthorRepository
) : FunSpec({

    context("CommentController") {
        test("댓글검색[최초검색, id를 안넣는 경우]") {
            // given
            val account = accountFixture(1L)
            accountRepository.save(account)
            val author = authorFixture()
            authorRepository.save(author)
            val webtoon = webtoonFixture(authors = listOf(author))
            webtoonRepository.save(webtoon)
            val comment = commentFixture(1L, account = account, webtoon = webtoon)
            commentRepository.save(comment)

            mockMvc.perform(
                MockMvcRequestBuilders
                    .get("/api/v1/comment/list")
                    .with(user("jaden"))
                    .param("webtoonId", webtoon.id.toString())
                    .param("commentId", null)
                    .param("pageSize", "2")


            ).andExpect {
                MockMvcResultMatchers.status().isOk
                MockMvcResultMatchers.content().json(
                    CommentsResponse(
                        isLastComment = true,
                        lastCommentId = null,
                        listOf(CommentResponse(
                            comment.id!!,
                            comment.account.convertToAccountResponse(),
                            comment.content!!,
                            comment.modifiedAt!!.toLocalDate()
                        ))
                    ).toString()
                )
            }

        }
    }
})
