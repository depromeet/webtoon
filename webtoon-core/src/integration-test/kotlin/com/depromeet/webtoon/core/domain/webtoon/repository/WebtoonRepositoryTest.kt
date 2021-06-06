package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.testsupport.AuthorTestDataHelper.Companion.save
import com.depromeet.webtoon.core.testsupport.WebtoonTestDataHelper.Companion.saveAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class WebtoonRepositoryTest constructor(
    var webtoonRepository: WebtoonRepository
) : FunSpec({
    context("Webtoon 생성 및 반환 확인") {
        test("create and selelct ") {
            // given
            val testWebtoon = Webtoon(title = "test", authors = listOf(), site = WebtoonSite.NAVER)

            // when
            val savedWebtoon = webtoonRepository.saveAndFlush(testWebtoon)

            // then
            val foundWebtoon = webtoonRepository.getOne(savedWebtoon.id!!)
            foundWebtoon.id.shouldNotBeNull()
            foundWebtoon.id shouldNotBe null
        }
    }

    context("webtoon fetchForAdmin") {
        test("fetchForAdmin 테스트") {
            // given
            val testAuthor = authorFixture().save()
            val testWebtoons = (0..9).map { webtoonFixture(authors = listOf(testAuthor)) }.saveAll()

            // when
            val webtoons = webtoonRepository.fetchForAdmin(3, 2)

            // then
            webtoons shouldHaveSize 2
            // then:: 역순으로 정렬함. 마지막에서부터 6번째(idx= 5), 5번째(idx= 4)가 선택됨
            webtoons shouldContainInOrder listOf(testWebtoons[5], testWebtoons[4])
        }
    }

    context("webtoon countForAdmin") {
        test("countForAdmin 테스트") {
            // given
            val testAuthor = authorFixture().save()
            (0..9).map { webtoonFixture(authors = listOf(testAuthor)) }.saveAll()

            // when
            val size = webtoonRepository.fetchCountForAdmin()

            // then
            size shouldBe 10
        }
    }
})
