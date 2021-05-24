package com.depromeet.webtoon.core.domain.banner.model

import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Banner(
    id: Long? = null,
    bannerType: BannerType = BannerType.NONE,
    caption: String = "",
    webtoon: Webtoon = Webtoon(),
    priority: Int = 0,
    displayBeginDateTime: LocalDateTime = LocalDateTime.MIN,
    displayEndDateTime: LocalDateTime = LocalDateTime.MIN,
    createdAt: LocalDateTime? = null,
    modifiedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = id

    @Enumerated(EnumType.STRING)
    @Column(name = "banner_type")
    var bannerType: BannerType = bannerType

    @Column(name = "caption")
    var caption: String = caption

    @ManyToOne
    @JoinColumn(name = "webtoon_id")
    var webtoon: Webtoon = webtoon

    @Column(name = "priority")
    var priority: Int = priority

    @Column(name = "display_start_date_time")
    var displayBeginDateTime: LocalDateTime = displayBeginDateTime

    @Column(name = "display_end_date_time")
    var displayEndDateTime: LocalDateTime = displayEndDateTime

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = createdAt
        private set

    @LastModifiedDate
    @Column(name = "modified_at")
    var modifiedAt: LocalDateTime? = modifiedAt
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Banner

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
