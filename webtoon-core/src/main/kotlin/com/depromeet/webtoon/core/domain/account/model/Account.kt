package com.depromeet.webtoon.core.domain.account.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Account(
    id: Long? = null,
    deviceId: String = "",
    nickname: String? = null,
    createdAt: LocalDateTime? = null,
    modifiedAt: LocalDateTime? = null,

) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = id

    var deviceId: String = deviceId

    var nickname: String? = nickname

    @CreatedDate
    var createdAt: LocalDateTime? = createdAt
        private set

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = modifiedAt
        private set
}
