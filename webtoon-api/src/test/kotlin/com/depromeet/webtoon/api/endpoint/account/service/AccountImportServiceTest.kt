package com.depromeet.webtoon.api.endpoint.account.service

import com.depromeet.webtoon.api.endpoint.account.AccountImportService
import com.depromeet.webtoon.api.endpoint.account.AccountNicknameGenerator
import com.depromeet.webtoon.api.endpoint.account.AccountProfileGenerator
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AccountImportServiceTest: FunSpec ({

    lateinit var accountRepository: AccountRepository
    lateinit var nicknameGenerator: AccountNicknameGenerator
    lateinit var profileGenerator: AccountProfileGenerator
    lateinit var accountImportService: AccountImportService

    beforeTest {
        accountRepository = mockk()
        nicknameGenerator = mockk()
        profileGenerator = mockk()
        accountImportService = AccountImportService(accountRepository, nicknameGenerator, profileGenerator)
    }

    context("AccountImportService"){
        test("loginAccount - 최초 생성시 닉네임, 프로필 생성 로직 타는지 확인"){

            // given
            every { accountRepository.findByAuthToken(any()) } returns null
            every { nicknameGenerator.generateNickname(any()) } returns accountFixture(id=1,nickname = "test")
            every { profileGenerator.generateProfileImage(any()) } returns accountFixture(id=1, nickname = "test", profileImage = "testImage")

            // when
            accountImportService.loginAccount("testToken")


            // then
            verify(exactly = 1) {
                accountRepository.findByAuthToken("testToken")
                nicknameGenerator.generateNickname(any())
                profileGenerator.generateProfileImage(any())
            }
        }

        test("loginAccount - 기존에 있는 경우 그냥 반환"){
            // given
            every { accountRepository.findByAuthToken(any()) } returns accountFixture(id=1, nickname = "nick", authToken = "testToken")

            // when
            accountImportService.loginAccount("testToken")

            // then
            verify(exactly = 1) {accountRepository.findByAuthToken("testToken")}
            verify(exactly = 0) {
                nicknameGenerator.generateNickname(any())
                profileGenerator.generateProfileImage(any())
            }


        }
    }
})
