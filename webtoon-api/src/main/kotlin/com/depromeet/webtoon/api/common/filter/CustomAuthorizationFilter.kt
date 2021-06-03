package com.depromeet.webtoon.api.common.filter

import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
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
        val account = accountRepository.findByAuthToken(token)

        if(account != null) {
            setSecurityContextWithAccount(account)
        }

        chain.doFilter(request, response)
    }


    private fun setSecurityContextWithAccount(account: Account) {
        val currentUser = createCurrentUser(account)
        val authentication = createAuthentication(currentUser, account)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun createAuthentication(
        currentUser: UserDetails,
        account: Account
    ): Authentication {
        return UsernamePasswordAuthenticationToken(currentUser, null, listOf(SimpleGrantedAuthority(account.authToken)))
    }

    private fun createCurrentUser(account: Account): UserDetails {
        return User.builder().username(account.id!!.toString()).password("").authorities(emptyList()).build()
    }


}
