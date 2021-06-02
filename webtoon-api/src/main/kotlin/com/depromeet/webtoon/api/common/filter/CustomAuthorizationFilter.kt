package com.depromeet.webtoon.api.common.filter

import com.depromeet.webtoon.api.common.CurrentUser
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CustomAuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val accountRepository: AccountRepository
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")

        // header 가 있는지 확인
        if (header == null || !header.startsWith("Bearer")) {
            chain.doFilter(request, response)
            return
        }

        val token = request.getHeader("Authorization").replace("Bearer ", "")
        val account = accountRepository.findByDeviceId(token)

        if(account != null) {
            val currentUser =  CurrentUser(account.deviceId, "", emptyList(), account.nickname)
            val authentication: Authentication = UsernamePasswordAuthenticationToken(currentUser, null, emptyList())
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain.doFilter(request, response)
    }



}
