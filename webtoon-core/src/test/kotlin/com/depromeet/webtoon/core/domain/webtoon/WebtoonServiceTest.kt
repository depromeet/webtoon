package com.depromeet.webtoon.core.domain.webtoon

import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.core.domain.webtoon.dto.convertToWebtoon
import com.depromeet.webtoon.core.domain.webtoon.dto.webtoonUpsertRequestFixture
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.domain.webtoon.service.WebtoonService
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class WebtoonServiceTest : FunSpec({

    lateinit var webtoonRepository: WebtoonRepository
    lateinit var webtoonService: WebtoonService

    beforeTest {
        webtoonRepository = mockk()
        webtoonService = WebtoonService(webtoonRepository)
    }

    test("getTop20WebtoonsByGenre") {
        every { webtoonRepository.get_Top10_Daum_Webtoons_ByGenre(any()) } returns listOf(webtoonFixture(1L), webtoonFixture(2L))
        every { webtoonRepository.get_Top10_Naver_Webtoons_ByGenre(any()) } returns listOf(webtoonFixture(3L))

        val result = webtoonService.getTop20WebtoonsByGenre("드라마")

        result.genre.shouldBe("드라마")
        result.top20Webtoons.size.shouldBe(3)
    }

    test("findById 없는 경우"){
        // given
        every { webtoonRepository.findById(any()) } returns Optional.empty()

        // when & then
        shouldThrow<IllegalArgumentException> {  webtoonService.findById(1L) }
        verify(exactly = 1) { webtoonRepository.findById(1L) }
    }

    test("findById 있는 경우") {
        // given
        every { webtoonRepository.findById(any()) } returns Optional.of(webtoonFixture())

        // when & then
        shouldNotThrow<Exception> {webtoonService.findById(1L)  }
        verify(exactly = 1) { webtoonRepository.findById(1L) }
    }

    test("getWeekdayWebtoons 호출 테스트") {
        // given
        every { webtoonRepository.findAllByWeekdaysOrderByPopularityAsc(any()) } returns listOf(webtoonFixture(1L), webtoonFixture(2L))

        // when
        val weekdayWebtoons = webtoonService.getWeekdayWebtoons(WeekDay.MON)

        // then
        verify(exactly = 1){ webtoonRepository.findAllByWeekdaysOrderByPopularityAsc(WeekDay.MON)  }
        weekdayWebtoons.size.shouldBe(2)
    }

    test("getWebtoons") {
        // given
        every { webtoonRepository.findAll() } returns listOf(
            webtoonFixture(title = "webtoon1"), webtoonFixture(title = "webtoon2")
        )
        // when
        val webtoons = webtoonService.getWebtoons()

        // then
        verify(exactly = 1) { webtoonRepository.findAll() }
        webtoons.map { it.title }.apply {
            this shouldContainExactly listOf("webtoon1", "webtoon2")
        }
    }

    test("upsertWebtoon 존재하는 웹툰일 경우 Update"){
        // given
        every { webtoonRepository.findBySiteAndTitle(any(), any()) } returns webtoonFixture(id = 2L, score = 1.0)
        val updateRequest = webtoonUpsertRequestFixture(score = 10.0)

        // when
        val upsertWebtoon = webtoonService.upsertWebtoon(updateRequest)

        // then
        upsertWebtoon.score.shouldBe(10.0)
    }

    test("upsertWebtoon 최초 웹툰인 경우 insert") {
        // given
        val updateRequest = webtoonUpsertRequestFixture()
        every { webtoonRepository.findBySiteAndTitle(any(), any()) } returns null
        every { webtoonRepository.save(any()) } returns updateRequest.convertToWebtoon()

        // when
        val upsertWebtoon = webtoonService.upsertWebtoon(updateRequest)

        // then
        verify(exactly = 1) {
            webtoonRepository.save(upsertWebtoon)
        }
    }

    test("getRandomWebtoons 호출 테스트") {
        // given
        every { webtoonRepository.find20RandomWebtoons() } returns listOf(webtoonFixture(1L), webtoonFixture(2L))

        // when
        val randomWebtoons = webtoonService.getRandomWebtoons()

        // then
        verify(exactly = 1) { webtoonRepository.find20RandomWebtoons() }
        randomWebtoons.size.shouldBe(2)
    }
})
