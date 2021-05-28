package com.depromeet.webtoon.core.domain.comment.model

import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.ManyToOne

@Entity
@EntityListeners(AuditingEntityListener::class)
class
Comment(
    id: Long? = null,
    content: String? = "",
    account: Account = Account(),
    webtoon: Webtoon = Webtoon(),
    nickname: String? = "",
    createdAt: LocalDateTime? = null,
    modifiedAt: LocalDateTime? = null,
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = id

    @ManyToOne(fetch = FetchType.LAZY)
    var account: Account = account

    @ManyToOne(fetch = FetchType.LAZY)
    var webtoon: Webtoon = webtoon

    var nickname: String? = nickname

    @Lob
    var content: String? = content

    @CreatedDate
    var createdAt: LocalDateTime? = createdAt
        private set

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = modifiedAt
        private set
}
