package com.depromeet.webtoon.core.testsupport

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import org.springframework.stereotype.Component

@Component
class WebtoonTestDataHelper(
    webtoonRepository: WebtoonRepository
) {
    init {
        WebtoonTestDataHelper.webtoonRepository = webtoonRepository
    }

    companion object {
        lateinit var webtoonRepository: WebtoonRepository

        fun List<Webtoon>.saveAll(): List<Webtoon> {
            return webtoonRepository.saveAll(this)
        }

        fun Webtoon.save(): Webtoon {
            return webtoonRepository.save(this)
        }
    }
}
