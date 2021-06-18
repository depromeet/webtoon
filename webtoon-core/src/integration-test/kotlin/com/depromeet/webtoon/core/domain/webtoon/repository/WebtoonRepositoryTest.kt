package com.depromeet.webtoon.core.domain.webtoon.repository

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.testsupport.AuthorTestDataHelper.Companion.save
import com.depromeet.webtoon.core.testsupport.WebtoonTestDataHelper.Companion.saveAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain
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

    context("장르별 점수 상위 10개") {
        test("get_Top10_Naver_Webtoons_ByGenre") {
            // given
            val webtoons = (0..20).map {
                webtoonFixture(
                    title = "네이버 웹툰$it",
                    site = WebtoonSite.NAVER,
                    genres = listOf("드라마"),
                    authors = listOf(),
                    score = it.toDouble()
                )
            }.saveAll()
            (20..30).map {
                webtoonFixture(
                    title = "다음 웹툰$it",
                    site = WebtoonSite.DAUM,
                    genres = listOf("드라마"),
                    authors = listOf(),
                    score = it.toDouble()
                )
            }.saveAll()

            // when
            val result = webtoonRepository.get_Top10_Naver_Webtoons_ByGenre("드라마")

            result.size shouldBe 10
            result.map { it.genres shouldNotContain WebtoonSite.DAUM }
            result shouldContainInOrder listOf(webtoons[20], webtoons[19], webtoons[15], webtoons[11])
        }

        test("get_Top10_Daum_Webtoons_ByGenre") {
            // given
            (0..20).map {
                webtoonFixture(
                    title = "네이버 웹툰$it",
                    site = WebtoonSite.NAVER,
                    genres = listOf("드라마"),
                    authors = listOf(),
                    score = it.toDouble()
                )
            }.saveAll()
            val webtoons = (20..30).map {
                webtoonFixture(
                    title = "다음 웹툰$it",
                    site = WebtoonSite.DAUM,
                    genres = listOf("드라마"),
                    authors = listOf(),
                    score = it.toDouble()
                )
            }.saveAll()

            // when
            val result = webtoonRepository.get_Top10_Daum_Webtoons_ByGenre("드라마")

            // then
            result.size shouldBe 10
            result.map { it.genres shouldNotContain WebtoonSite.DAUM }
            result shouldContainInOrder listOf(webtoons[10], webtoons[9], webtoons[5], webtoons[1])
        }
    }

    context("완결웹툰") {
        test("getCompletedWebtoons") {
            (1..10).map {
                webtoonFixture(
                    title = "네이버 웹툰$it",
                    site = WebtoonSite.NAVER,
                    isComplete = true
                )
            }.saveAll()

            (1..10).map {
                webtoonFixture(
                    title = "다음 웹툰$it",
                    site = WebtoonSite.DAUM,
                    isComplete = false
                )
            }.saveAll()

            val testCompleteWebtoons = webtoonRepository.getCompletedWebtoons(null, 20)

            testCompleteWebtoons shouldHaveSize 10
            testCompleteWebtoons.map { it.genres shouldNotContain WebtoonSite.DAUM }
        }
    }

    // todo 테스트가 깨질 수도 있는데 어떻게 고쳐야하지.. ? 약 100만분의 1확률
    context("장르추천 네이버,다음 섞여서 나오는지 테스트"){
        test("genreRecommendWebtoon"){

            (1..10).map {
                webtoonFixture(
                    title = "네이버 웹툰$it",
                    site = WebtoonSite.NAVER,
                    authors = listOf(),
                    genres = listOf("드라마"),
                    isComplete = true
                )
            }.saveAll()

            (1..10).map {
                webtoonFixture(
                    title = "다음 웹툰$it",
                    site = WebtoonSite.DAUM,
                    authors = listOf(),
                    genres = listOf("드라마"),
                    isComplete = false
                )
            }.saveAll()

            val resultList = arrayListOf<WebtoonSite>()
            for (i in 1..20) {
                val each = webtoonRepository.getGenreRecommendWebtoon("드라마")
                resultList.add(each!!.site)
            }

            resultList shouldContainAll listOf(WebtoonSite.DAUM, WebtoonSite.NAVER)
        }
    }


})
