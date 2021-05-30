package com.depromeet.webtoon.admin.api.auth

import com.depromeet.webtoon.admin.config.JwtTokenProvider
import com.depromeet.webtoon.core.domain.adminuser.model.AdminUser
import com.depromeet.webtoon.core.domain.adminuser.repository.AdminUserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authenticationManager: AuthenticationManager,
    val jwtTokenProvider: JwtTokenProvider,

    val passwordEncoder: PasswordEncoder,
    val adminUserRepository: AdminUserRepository
) {

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val token: String = jwtTokenProvider.generateToken(authentication.name)

        return ResponseEntity.ok("auth" to token)
    }

    @PostConstruct
    fun addSampleAdminUser() {

        adminUserRepository.save(AdminUser("어드민", "toonie_admin", passwordEncoder.encode("1q2w3e4r!!")))
    }
}
