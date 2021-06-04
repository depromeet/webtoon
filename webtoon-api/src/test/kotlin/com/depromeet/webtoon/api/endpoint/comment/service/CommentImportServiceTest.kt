import com.depromeet.webtoon.api.endpoint.comment.dto.CreateCommentRequest
import com.depromeet.webtoon.api.endpoint.comment.dto.UpdateCommentRequest
import com.depromeet.webtoon.api.endpoint.comment.service.CommentImportService
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.domain.comment.commentFixture
import com.depromeet.webtoon.core.domain.comment.repository.CommentRepository
import com.depromeet.webtoon.core.domain.webtoon.model.webtoonFixture
import com.depromeet.webtoon.core.domain.webtoon.repository.WebtoonRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import java.time.LocalDateTime
import java.util.*


class CommentImportServiceTest: FunSpec({

    lateinit var commentImportService: CommentImportService
    lateinit var commentRepository: CommentRepository
    lateinit var webtoonRepository: WebtoonRepository
    lateinit var accountRepository: AccountRepository



    beforeTest {
        commentRepository = mockk()
        webtoonRepository = mockk()
        accountRepository = mockk()
        commentImportService = CommentImportService(commentRepository, webtoonRepository, accountRepository)
        SecurityContextHolder.getContext().authentication =
            PreAuthenticatedAuthenticationToken(1, null, listOf(SimpleGrantedAuthority("ROLE_USER")))
    }

    context("CommentImportServiceTest"){

        test("createComment"){
            // given
            val account = accountFixture(1L)
            val webtoon = webtoonFixture(1L)
            val newComment = commentFixture(1L, "funny", account, webtoon, account.nickname, LocalDateTime.now(), LocalDateTime.now())

            every { accountRepository.findById(any()) } returns Optional.of(account)
            every { webtoonRepository.findById(any()) } returns Optional.of(webtoon)
            every { commentRepository.save(any()) } returns newComment

            // when
            commentImportService.createComment(CreateCommentRequest(webtoon.id!!, newComment.content!!))

            // ten
            verify(exactly = 1) {
                accountRepository.findById(account.id!!)
                webtoonRepository.findById(webtoon.id!!)
                commentRepository.save(any())
            }
        }

        test("updateComment"){
            // given
            val updateCommentRequest = UpdateCommentRequest(1L, "fun")
            val originalComment = commentFixture(1L, "funny")
            val newComment = commentFixture(1L,"fun")

            every { commentRepository.findById(any()) } returns Optional.of(originalComment)
            every { commentRepository.save(any()) } returns newComment
            every { accountRepository.findById(any()) } returns Optional.of(accountFixture(1L))


            // when
            commentImportService.updateComment(updateCommentRequest)

            // ten
            verify(exactly = 1) {
                commentRepository.findById(updateCommentRequest.commentId)
                commentRepository.save(any())
            }
        }

        test("updateComment 실패 (작성자와 수정자의 id 가 다른경우"){
            // given
            val updateCommentRequest = UpdateCommentRequest(1L, "fun")
            val originalComment = commentFixture(1L, "funny")

            every { commentRepository.findById(any()) } returns Optional.of(originalComment)
            every { accountRepository.findById(any()) } returns Optional.of(accountFixture(2L))


            // when then
            shouldThrow<ApiValidationException> {
                commentImportService.updateComment(updateCommentRequest)
            }
            verify(exactly = 1) {
                commentRepository.findById(updateCommentRequest.commentId)
            }
        }

        test("deleteComment"){
            val originalComment = commentFixture(1L, "funny")

            every { commentRepository.findById(any()) } returns Optional.of(originalComment)
            every { accountRepository.findById(any()) } returns Optional.of(accountFixture(1L))
            every { commentRepository.delete(any()) } returns Unit

            // when
            commentImportService.deleteComment(originalComment.id!!)

            // ten
            verify(exactly = 1) {
                commentRepository.findById(originalComment.id!!)
                commentRepository.delete(originalComment)
            }
        }

        test("deleteComment 실패 (작성자와 삭제자가 다른 경우"){
            val originalComment = commentFixture(1L, "funny")

            every { commentRepository.findById(any()) } returns Optional.of(originalComment)
            every { accountRepository.findById(any()) } returns Optional.of(accountFixture(2L))
            every { commentRepository.delete(any()) } returns Unit

            // when
            shouldThrow<ApiValidationException> {
                commentImportService.deleteComment(originalComment.id!!)
            }
            // ten
            verify(exactly = 1) {
                commentRepository.findById(originalComment.id!!)
            }
        }
    }

})
