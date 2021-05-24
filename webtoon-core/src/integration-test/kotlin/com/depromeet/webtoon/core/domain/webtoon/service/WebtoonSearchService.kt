package com.depromeet.webtoon.core.domain.webtoon.service

import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.testsupport.AuthorTestDataHelper.Companion.save
import com.depromeet.webtoon.core.testsupport.AuthorTestDataHelper.Companion.saveAll
import com.depromeet.webtoon.core.testsupport.WebtoonTestDataHelper.Companion.saveAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class WebtoonSearchServiceIntegrationTest constructor(
    val webtoonSearchService: WebtoonSearchService,
) : FunSpec({

    context("텍스트 기준 웹툰 조회") {
        test("제목 매칭 테스트") {
            // given
            val testAuthor = authorFixture(id = 1L, name = "작가")
                .save()

            listOf(
                webtoonFixture(id = 1L, title = "테스트1", authors = listOf(testAuthor)),
                webtoonFixture(id = 2L, title = "테스트2", authors = listOf(testAuthor)),
                webtoonFixture(id = 3L, title = "안녕", authors = listOf(testAuthor))
            ).saveAll()

            // when
            val searchResults = webtoonSearchService.searchByQueryStr("테스트")

            // then
            searchResults shouldHaveSize 2
            searchResults.map { it.title } shouldContainAll listOf("테스트1", "테스트2")
        }

        test("작가 매칭 테스트") {
            val testAuthors = listOf(
                authorFixture(name = "승진"),
                authorFixture(name = "지윤")
            ).saveAll()

            listOf(
                webtoonFixture(id = 1L, title = "테스트1", authors = listOf(testAuthors[0])),
                webtoonFixture(id = 2L, title = "테스트2", authors = listOf(testAuthors[1])),
                webtoonFixture(id = 3L, title = "안녕", authors = listOf(testAuthors[0]))
            ).saveAll()

            // when
            val searchResults = webtoonSearchService.searchByQueryStr("승진")

            // then
            searchResults shouldHaveSize 2
            searchResults.map { it.title } shouldContainAll listOf("테스트1", "안녕")
        }
    }
})
