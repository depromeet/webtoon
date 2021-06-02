package com.depromeet.webtoon.core.domain.adminuser.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@EntityListeners(AuditingEntityListener::class)
class AdminUser(name: String = "", username: String = "", password: String = "") {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String = name

    @NotBlank
    @Column(name = "username", unique = true)
    @Size(max = 15)
    // 테스트 짜기 귀찮다...
    @Pattern(regexp = "^[a-z0-9_]+$")
    var username: String = username

    @NotBlank
    @Size(max = 100)
    var password: String = password

    @CreatedDate
    var createdAt: LocalDateTime? = null
        private set

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = null
        private set
}
