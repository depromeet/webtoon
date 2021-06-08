package com.depromeet.webtoon.api.endpoint.account.service

import com.depromeet.webtoon.api.endpoint.account.AccountNicknameGenerator
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.core.io.ResourceLoader
import java.io.ByteArrayInputStream

class AccountNicknameGeneratorTest: FunSpec ({

    lateinit var resourceLoader: ResourceLoader
    lateinit var accountRepository: AccountRepository
    lateinit var accountNicknameGenerator: AccountNicknameGenerator

    val mockNicknameNounFile = "명사"
    val mockNicknamePrefixFile = "접두어"
    val nounInputStream = ByteArrayInputStream(mockNicknameNounFile.toByteArray())
    val prefixInputStream = ByteArrayInputStream(mockNicknamePrefixFile.toByteArray())

    beforeTest {
        resourceLoader = mockk()
        accountRepository = mockk()
        accountNicknameGenerator = AccountNicknameGenerator(resourceLoader, accountRepository)
    }

    context("AccountNicknameGenerator"){
        test("닉네임 생성확인"){

            // given
            val newAccount = accountFixture(1L, nickname = "")
            every { accountRepository.save(any()) } returns newAccount
            every { resourceLoader.getResource("classpath:nickname/nicknamenoun.txt").inputStream} returns nounInputStream
            every { resourceLoader.getResource("classpath:nickname/nicknameprefix.txt").inputStream } returns prefixInputStream

            // when
            val createdAccount = accountNicknameGenerator.saveAccountWithGeneratedNickname(accountFixture(1L))

            // then
            createdAccount.nickname.shouldNotBe("")
            createdAccount.nickname.shouldBe("$mockNicknamePrefixFile $mockNicknameNounFile#${newAccount.id}")
        }
    }

})
