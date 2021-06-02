package com.depromeet.webtoon.api.common.filter

import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import io.kotest.core.spec.style.FunSpec
import org.springframework.security.authentication.AuthenticationManager
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CustomAuthorizationFilterTest : FunSpec({

    lateinit var authenticationManager: AuthenticationManager
    lateinit var accountRepository: AccountRepository
    lateinit var httpServletRequest: HttpServletRequest
    lateinit var httpServletResponse: HttpServletResponse
    lateinit var filterChain: FilterChain
    lateinit var customAuthorizationFilter: CustomAuthorizationFilter

    beforeTest {

        customAuthorizationFilter = CustomAuthorizationFilter(authenticationManager, accountRepository)
    }

    context("CustomAuthorizationFilterTest") {
        test("doFilterInternal") {

        }
    }

})
