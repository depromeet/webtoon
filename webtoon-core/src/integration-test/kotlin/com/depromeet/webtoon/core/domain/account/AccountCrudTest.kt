package com.depromeet.webtoon.core.domain.account

import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
class AccountCrudTest (
    val accountRepository: AccountRepository
        ) : FunSpec ({

    test("Create Account"){
        // given
        val account = Account(
            null,
            "testDevice",
            "testNick",
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
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        val savedAccount = accountRepository.save(account)


        // when
        val foundAccount = accountRepository.findById(savedAccount.id!!)

        // then
        foundAccount.get().deviceId.shouldBeEqualComparingTo(account.deviceId)
        foundAccount.get().nickname!!.shouldBeEqualComparingTo(account.nickname!!)
    }

    test("Update Account"){
        // given
        val account = Account(
            null,
            "testDevice",
            "testNick",
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
