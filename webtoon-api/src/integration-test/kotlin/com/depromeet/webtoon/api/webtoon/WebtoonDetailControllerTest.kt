package com.depromeet.webtoon.api.webtoon

import com.depromeet.webtoon.api.endpoint.dto.AuthorResponse
import com.depromeet.webtoon.api.endpoint.dto.ScoreResponse
import com.depromeet.webtoon.api.endpoint.dto.WebtoonDetailResponse
import com.depromeet.webtoon.api.endpoint.webtoon.WebtoonDetailController
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.review.model.Review
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
// @EnableAutoConfiguration(exclude = [SampleRunner::class])
@AutoConfigureMockMvc
@ActiveProfiles("webtoon-local-test")
class WebtoonDetailControllerTest(
    var webtoonDetailController: WebtoonDetailController,
    var mockMvc: MockMvc,
    var webtoonRepository: WebtoonRepository,
    var reviewRepository: ReviewRepository,
    var accountRepository: AccountRepository,
    var authorRepository: AuthorRepository
) : FunSpec({

    test("GET  /api/v1/webtoons/detail"){
        // given
        val account = Account(null, "testDevice", null, null, null)
        accountRepository.save(account)
        val webtoon = webtoonFixture()
        webtoonRepository.save(webtoon)
        val author = authorFixture()
        authorRepository.save(author)

        val review = Review(null, webtoon, account, "fun", 3.5, 3.5, null, null)
        reviewRepository.save(review)

        mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/webtoons/detail")
            .param("id", "1"))
            .andExpect {
                MockMvcResultMatchers.status().isOk
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
                MockMvcResultMatchers.content().json(
                    WebtoonDetailResponse(
                        1L,
                        webtoon.title,
                        webtoon.thumbnail,
                        webtoon.url,
                        webtoon.authors.map { AuthorResponse(it.id!!, it.name)},
                        webtoon.site,
                        webtoon.weekdays,
                        webtoon.summary,
                        ScoreResponse(review.storyScore!!, review.drawingScore!!),
                        listOf(review.comment)
                    ).toString()
                )
            }
    }

})
