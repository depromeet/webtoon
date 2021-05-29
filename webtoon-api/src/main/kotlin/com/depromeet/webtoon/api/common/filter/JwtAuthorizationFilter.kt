package com.depromeet.webtoon.api.common.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.depromeet.webtoon.api.common.CurrentUser
import com.depromeet.webtoon.api.config.JsonWebToken
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val jsonWebToken: JsonWebToken,
    private val accountRepository: AccountRepository
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val jwtHeader = request.getHeader("Authorization")

        // header 가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response)
            return
        }
        // jwt 검증해서 정상사용자인지 확인
        val jwtToken = request.getHeader("Authorization").replace("Bearer ", "")

        val deviceId = JWT.require(Algorithm.HMAC512(jsonWebToken.secure)).build().verify(jwtToken)
            .getClaim("deviceId").asString()

        if (deviceId != null) {
            val account = accountRepository.findByDeviceId(deviceId)
            val currentUser =  CurrentUser(account!!.deviceId, "", emptyList(), account.nickname)
            val authentication: Authentication = UsernamePasswordAuthenticationToken(currentUser, null, emptyList())

            // SecurityContextHolder 에 직접 Authentication 객체 주입
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain.doFilter(request, response)
    }
}
