package com.depromeet.webtoon.domain.webtoon

import io.kotest.core.spec.style.FunSpec
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WebtoonRepositoryTest constructor(
    var webtoonRepository: WebtoonRepository
) : FunSpec({

    test("Webtoon 생성 및 반환 확인") {
        // given
        val testWebtoon = Webtoon("웹툰")

        // when
        val savedWebtoon = webtoonRepository.saveAndFlush(testWebtoon)

        // then
        val foundWebtoon = webtoonRepository.getOne(savedWebtoon.id!!)
        assertThat(foundWebtoon.id).isNotNull()
    }
})
