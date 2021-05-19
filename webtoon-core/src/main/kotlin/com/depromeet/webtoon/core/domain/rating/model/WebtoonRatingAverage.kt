package com.depromeet.webtoon.core.domain.rating.model

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
import javax.persistence.ManyToOne

@Entity
@EntityListeners(AuditingEntityListener::class)
class WebtoonRatingAverage(
    id: Long? = null,
    webtoon: Webtoon = Webtoon(),
    totalStoryScore: Double? = null,
    totalDrawingScore: Double? = null,
    votes: Long? = null,
    storyAverage: Double? = null,
    drawingAverage: Double? = null,
    totalAverage: Double? = null,
    createdAt: LocalDateTime? = null,
    modifiedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = id

    @ManyToOne(fetch = FetchType.LAZY)
    val webtoon: Webtoon = webtoon

    var totalStoryScore: Double? = totalStoryScore

    var totalDrawingScore: Double? = totalDrawingScore

    var votes: Long? = votes

    var storyAverage: Double? = storyAverage

    var drawingAverage: Double? = drawingAverage

    var totalAverage: Double? = totalAverage

    @CreatedDate
    var createdAt: LocalDateTime? = createdAt
        private set

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = modifiedAt
        private set
}
