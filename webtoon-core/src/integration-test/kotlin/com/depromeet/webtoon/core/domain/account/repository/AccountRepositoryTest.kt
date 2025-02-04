package com.depromeet.webtoon.core.domain.account.repository

import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.model.Account
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@Transactional
class AccountRepositoryTest(
    val accountRepository: AccountRepository
) : FunSpec({

    test("findByAuthToken"){
        val account = accountFixture()
        accountRepository.save(account)
        val findAccount = accountRepository.findByAuthToken(account.authToken)
        findAccount.shouldNotBeNull()
        findAccount.id.shouldBe(account.id)
    }

    test("findByNickname"){
        val account = accountFixture()
        accountRepository.save(account)
        val findAccount = accountRepository.findByNickname(account.nickname)
        findAccount.shouldNotBeNull()
        findAccount.id.shouldBe(account.id)
    }

    test("Create Account") {
        // given
        val account = Account(
            null,
            "testDevice",
            "testNick",
            "profileImage",
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // when
        val savedAccount = accountRepository.save(account)

        // then
        savedAccount.id.shouldNotBeNull()
    }

    test("Select Account") {
        // given
        val account = Account(
            null,
            "testDevice",
            "testNick",
            "profileImage",
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        val savedAccount = accountRepository.save(account)

        // when
        val foundAccount = accountRepository.findById(savedAccount.id!!)

        // then
        foundAccount.get().authToken.shouldBeEqualComparingTo(account.authToken)
        foundAccount.get().nickname!!.shouldBeEqualComparingTo(account.nickname!!)
    }

    test("Update Account") {
        // given
        val account = Account(
            null,
            "testDevice",
            "testNick",
            "profileImage",
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        val savedAccount = accountRepository.save(account)

        // when
        val newName = "newName"
        savedAccount.nickname = newName
        accountRepository.save(savedAccount)

        // then
        val updatedAccount = accountRepository.findById(savedAccount.id!!)
        updatedAccount.get().nickname!!.shouldBeEqualComparingTo(newName)
    }

    test("Delete Account") {
        // given
        val account = Account(
            null,
            "testDevice",
            "testNick",
            "profileImage",
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        val savedAccount = accountRepository.save(account)

        // when
        // accountRepository.deleteById(savedAccount.id!!)
        /*
        테스트 수행후 rollback 이 안된다. @Transactional 이 안먹음
         */
        accountRepository.deleteAll()

        // then
        accountRepository.findAll().shouldHaveSize(0)
    }
})
