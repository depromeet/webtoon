package com.depromeet.webtoon.api.webtoon

import com.depromeet.webtoon.api.endpoint.account.AccountAuthService
import com.depromeet.webtoon.api.endpoint.account.AccountUpdateService
import com.depromeet.webtoon.core.domain.account.accountFixture
import com.depromeet.webtoon.core.domain.account.dto.NicknameUpdateResponse
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest(
    val mockMvc: MockMvc,
    val updateService: AccountUpdateService,
    val authService: AccountAuthService,
    val accountRepository: AccountRepository
): FunSpec ({

    context("AccountController"){
        test("닉네임 변경 - 정상동작"){
            // nickname 존재
            val account = accountFixture(id=1L, nickname = "기존")
            val originAccount = accountRepository.save(account)

            mockMvc.perform(
                MockMvcRequestBuilders
                    .patch("/api/v1/nickname")
                    .with(user("1"))
                    .param("newNickname", "변경")
            ).andExpect{
                status().isOk
                content().json(
                    NicknameUpdateResponse(
                        accountId = 1L,
                        nickname = "변경",
                        profileImage = null
                    ).toString()
                )
            }
        }
    }

})
