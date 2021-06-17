package com.depromeet.webtoon.core.domain.comment.repository

import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.author.authorFixture
import com.depromeet.webtoon.core.domain.author.repository.AuthorRepository
import com.depromeet.webtoon.core.domain.comment.commentFixture
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@SpringBootTest
@Transactional
class CommentRepositoryTest(
    val commentRepository: CommentRepository,
    val accountRepository: AccountRepository,
    val webtoonRepository: WebtoonRepository,
    val authorRepository: AuthorRepository,
    ): FunSpec({

    val webtoon = webtoonFixture()
    val author = authorFixture()

    beforeTest {
        authorRepository.save(author)
        webtoonRepository.save(webtoon)

        for(i: Int in 1..10){
            accountRepository.save(accountFixture(id = i.toLong()))
            commentRepository.save(commentFixture(
                    id = i.toLong(),
                    content = "재밌네요 $i",
                    account = accountFixture(id = i.toLong()),
                    webtoon = webtoon,
                    createdAt = LocalDateTime.now().plusMinutes(i.toLong())
            ))
        }
    }

    context("Comment Repository"){
        test("findTop5ByWebtoonOrderByCreatedAtDesc 쿼리"){
            val result = commentRepository.findTop5ByWebtoonOrderByCreatedAtDesc(webtoon)
            result!!.size.shouldBe(5)
            // 최신순 정렬
            result[0].id.shouldBe(10)
        }

        test("getComments 페이징 사이즈 제대로 동작하는지 확인"){
            var comments = commentRepository.getComments(webtoon.id!!, null, 10)
            comments.size.shouldBe(10)
            comments = commentRepository.getComments(webtoon.id!!, 9L, pageSize = 5)
            comments.size.shouldBe(5)
        }
    }
})
