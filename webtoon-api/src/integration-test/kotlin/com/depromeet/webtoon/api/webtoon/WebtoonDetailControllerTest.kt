package com.depromeet.webtoon.api.webtoon

import com.depromeet.webtoon.api.endpoint.dto.AuthorResponse
import com.depromeet.webtoon.api.endpoint.dto.ScoreResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.comment.commentFixture
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import com.depromeet.webtoon.core.domain.rating.dto.CommentDto
import com.depromeet.webtoon.core.domain.rating.model.Rating
import com.depromeet.webtoon.core.domain.rating.repository.RatingRepository
import com.depromeet.webtoon.core.domain.rating.repository.WebtoonRatingAverageRepository
import com.depromeet.webtoon.core.domain.rating.webtoonRatingAverageFixture
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class WebtoonDetailControllerTest(
    var mockMvc: MockMvc,
    var webtoonRepository: WebtoonRepository,
    var webtoonRatingAverageRepository: WebtoonRatingAverageRepository,
    var commentRepository: CommentRepository,
    var accountRepository: AccountRepository,
    var authorRepository: AuthorRepository
) : FunSpec({

    test("GET  /api/v1/webtoons/detail") {
        // given
        val account = accountFixture(1L)
        accountRepository.save(account)
        val author = authorFixture()
        authorRepository.save(author)
        val webtoon = webtoonFixture(authors = listOf(author))
        webtoonRepository.save(webtoon)
        val webtoonRatingAvg = webtoonRatingAverageFixture(1L, webtoon = webtoon)
        webtoonRatingAverageRepository.save(webtoonRatingAvg)
        val comment = commentFixture(1L, account = account, webtoon = webtoon)
        commentRepository.save(comment)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/v1/webtoons/detail")
                .param("id", "1")
        )
            .andExpect {
                MockMvcResultMatchers.status().isOk
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
                MockMvcResultMatchers.content().json(
                    WebtoonDetailResponse(
                        1L,
                        webtoon.title,
                        webtoon.thumbnail,
                        webtoon.url,
                        webtoon.authors.map { AuthorResponse(it.id!!, it.name) },
                        webtoon.genres,
                        webtoon.site,
                        webtoon.weekdays,
                        webtoon.summary,
                        ScoreResponse(webtoonRatingAvg.totalAverage!!, webtoonRatingAvg.totalStoryScore!!, webtoonRatingAvg.drawingAverage!!),
                        comments = listOf(CommentDto(comment.content, comment.nickname))
                    ).toString()
                )
            }
    }
})
