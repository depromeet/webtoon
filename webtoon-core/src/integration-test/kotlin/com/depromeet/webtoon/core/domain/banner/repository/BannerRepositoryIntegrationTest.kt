package com.depromeet.webtoon.core.domain.banner.repository

import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.banner.bannerFixture
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.testsupport.AuthorTestDataHelper.Companion.save
import com.depromeet.webtoon.core.testsupport.BannerTestDataHelper.Companion.saveAll
import com.depromeet.webtoon.core.testsupport.WebtoonTestDataHelper.Companion.save
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class BannerRepositoryIntegrationTest(val bannerRepository: BannerRepository) : FunSpec({
    context("banner fetchForAdmin") {
        test("fetchForAdmin 테스트") {
            // given
            val testWebtoon = webtoonFixture(authors = listOf(authorFixture().save())).save()
            val testBanners = (0..9).map { bannerFixture(webtoon = testWebtoon) }.saveAll()

            // when
            val banners = bannerRepository.fetchForAdmin(3, 2)

            // then
            banners shouldHaveSize 2
            // then:: 역순으로 정렬함. 마지막에서부터 6번째(idx= 5), 5번째(idx= 4)가 선택됨
            banners shouldContainInOrder listOf(testBanners[5], testBanners[4])
        }
    }

    context("banner countForAdmin") {
        test("countForAdmin 테스트") {
            // given
            val testWebtoon = webtoonFixture(authors = listOf(authorFixture().save())).save()
            (0..9).map { bannerFixture(webtoon = testWebtoon) }.saveAll()

            // when
            val size = bannerRepository.fetchCountForAdmin()

            // then
            size shouldBe 10
        }
    }
})
