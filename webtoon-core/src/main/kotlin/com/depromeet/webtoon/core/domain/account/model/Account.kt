package com.depromeet.webtoon.core.domain.account.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
@EntityListeners(AuditingEntityListener::class)
class Account(
    id: Long? = null,
    authToken: String = "",
    nickname: String = "",
    profileImage: String = "",
    createdAt: LocalDateTime? = null,
    modifiedAt: LocalDateTime? = null,
    ) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = id

    var authToken: String = authToken

    var nickname: String = nickname

    var profileImage: String = profileImage

    @CreatedDate
    var createdAt: LocalDateTime? = createdAt
        private set

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = modifiedAt
        private set
}
