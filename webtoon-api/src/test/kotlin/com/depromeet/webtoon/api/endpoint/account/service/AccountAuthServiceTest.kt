package com.depromeet.webtoon.api.endpoint.account.service

import com.depromeet.webtoon.api.endpoint.account.AccountAuthService
import com.depromeet.webtoon.api.endpoint.account.AccountNicknameGenerator
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AccountAuthServiceTest: FunSpec ({

    lateinit var accountRepository: AccountRepository
    lateinit var accountNicknameGenerator: AccountNicknameGenerator
    lateinit var accountAuthService: AccountAuthService

    beforeTest {
        accountRepository = mockk()
        accountNicknameGenerator = mockk()
        accountAuthService = AccountAuthService(accountRepository, accountNicknameGenerator)
    }

    context("AccountAuthService"){
        test("loginAccount - 최초 생성시 닉네임 생성 로직 타는지 확인"){

            // given
            every { accountRepository.findByAuthToken(any()) } returns null
            every { accountNicknameGenerator.saveAccountWithGeneratedNickname(any()) } returns accountFixture(id=1,nickname = "test")

            // when
            accountAuthService.loginAccount("testToken")


            // then
            verify(exactly = 1) {
                accountRepository.findByAuthToken("testToken")
                accountNicknameGenerator.saveAccountWithGeneratedNickname(any())
            }
        }

        test("loginAccount - 기존에 있는 경우 그냥 반환"){
            // given
            every { accountRepository.findByAuthToken(any()) } returns accountFixture(id=1, nickname = "nick", authToken = "testToken")

            // when
            accountAuthService.loginAccount("testToken")

            // then
            verify(exactly = 1) {accountRepository.findByAuthToken("testToken")}
            verify(exactly = 0) { accountNicknameGenerator.saveAccountWithGeneratedNickname(any()) }

        }
    }
})
