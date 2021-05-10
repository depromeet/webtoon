package com.depromeet.webtoon.core.domain.webtoon.model

import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.type.WebtoonSite
import com.depromeet.webtoon.core.type.WebtoonSite.NONE
import com.depromeet.webtoon.core.type.WeekDay
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany

@Entity
@EntityListeners(AuditingEntityListener::class)
class Webtoon constructor(
    id: Long? = null,
    title: String = "",
    site: WebtoonSite = NONE,
    url: String = "",
    authors: List<Author> = mutableListOf(),
    weekdays: List<WeekDay> = mutableListOf(),
    popularity: Int = 0,
    thumbnail: String = "",
    summary: String = "",
    createdAt: LocalDateTime? = null,
    modifiedAt: LocalDateTime? = null,
) {
    constructor() : this(id = null)

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    var id: Long? = id

    @Column(name = "title")
    var title: String = title

    @Column(name = "site")
    @Enumerated(EnumType.STRING)
    var site: WebtoonSite = site

    @Column(name = "uri")
    var url: String = url

    @ManyToMany
    var authors: MutableList<Author> = authors.toMutableList()

    @ElementCollection
    @CollectionTable(name = "week_day", joinColumns = [JoinColumn(name = "id")])
    @Enumerated(EnumType.STRING)
    var weekdays: MutableList<WeekDay> = weekdays.toMutableList()

    @Column(name = "popularity")
    var popularity: Int = popularity

    @Column(name = "thumbnail")
    var thumbnail: String = thumbnail

    @Column(name = "summary")
    var summary: String = summary

    @CreatedDate
    var createdAt: LocalDateTime? = createdAt
        private set

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = modifiedAt
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Webtoon

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
