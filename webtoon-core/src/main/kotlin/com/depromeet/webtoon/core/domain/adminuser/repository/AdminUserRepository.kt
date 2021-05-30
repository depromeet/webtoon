package com.depromeet.webtoon.core.domain.adminuser.repository

import com.depromeet.webtoon.core.domain.adminuser.model.AdminUser
import org.springframework.data.jpa.repository.JpaRepository

interface AdminUserRepository : JpaRepository<AdminUser, Long> {
    fun findByUsername(username: String): AdminUser?
}
