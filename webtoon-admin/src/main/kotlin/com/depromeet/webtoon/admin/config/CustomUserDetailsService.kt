package com.depromeet.webtoon.admin.config

import com.depromeet.webtoon.core.domain.adminuser.model.AdminUser
import com.depromeet.webtoon.core.domain.adminuser.repository.AdminUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    val adminUserRepository: AdminUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user: AdminUser = adminUserRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username or email : $username")
        return UserPrincipal(user.id!!, user.name, user.username, user.password)
    }
}
