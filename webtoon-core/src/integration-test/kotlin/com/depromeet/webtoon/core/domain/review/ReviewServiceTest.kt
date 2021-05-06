package com.depromeet.webtoon.core.domain.review

import com.depromeet.webtoon.core.domain.review.model.Review
import com.depromeet.webtoon.core.domain.review.repository.ReviewRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReviewServiceTest(
    val reviewRepository: ReviewRepository
) : FunSpec({

    test("test"){
        reviewRepository.save(Review())
    }


})
