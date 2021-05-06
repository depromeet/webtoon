package com.depromeet.webtoon.core.domain.review.model

import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Review(
    id: Long? = null,
    webtoon: Webtoon = Webtoon(),
    account: Account = Account(),
    comment: String = "",
    storyScore: Double? = null,
    drawingScore: Double? = null,
    createdAt: LocalDateTime? = null,
    modifiedAt: LocalDateTime? = null,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = id

    @ManyToOne(fetch = FetchType.LAZY)
    val webtoon: Webtoon = webtoon

    @ManyToOne(fetch = FetchType.LAZY)
    val account: Account = account

    val comment: String = comment

    val storyScore: Double? = storyScore

    val drawingScore: Double? = drawingScore

    @CreatedDate
    var createdAt: LocalDateTime? = createdAt
        private set

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = modifiedAt
        private set
}
