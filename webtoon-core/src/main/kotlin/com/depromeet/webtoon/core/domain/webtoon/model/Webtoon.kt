package com.depromeet.webtoon.core.domain.webtoon.model

import com.depromeet.webtoon.common.type.WebtoonSite
import com.depromeet.webtoon.common.type.WeekDay
import com.depromeet.webtoon.core.domain.author.model.Author
import com.depromeet.webtoon.core.domain.webtoon.dto.WebtoonUpsertRequest
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
import javax.persistence.JoinTable
import javax.persistence.Lob
import javax.persistence.ManyToMany

@Entity
@EntityListeners(AuditingEntityListener::class)
class Webtoon constructor(
    id: Long? = null,
    title: String = "",
    site: WebtoonSite = WebtoonSite.NONE,
    url: String = "",
    authors: List<Author> = mutableListOf(),
    weekdays: List<WeekDay> = mutableListOf(),
    popularity: Int = 0,
    thumbnail: String = "",
    summary: String = "",
    score: Double = 0.0,
    genres: List<String> = mutableListOf(),
    backgroundColor: String = "",
    isComplete: Boolean? = true,
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

    @Column(name = "url")
    var url: String = url

    @ManyToMany
    @JoinTable(
        name = "webtoon_author",
        joinColumns = [JoinColumn(name = "webtoon_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")]
    )
    var authors: MutableList<Author> = authors.toMutableList()

    @ElementCollection
    @CollectionTable(name = "webtoon_week_day", joinColumns = [JoinColumn(name = "webtoon_id")])
    @Enumerated(EnumType.STRING)
    var weekdays: MutableList<WeekDay> = weekdays.toMutableList()

    @Column(name = "popularity")
    var popularity: Int = popularity

    @Column(name = "thumbnail")
    var thumbnail: String = thumbnail

    @Lob
    @Column(name = "summary")
    var summary: String = summary

    @Column(name = "score")
    var score: Double = score

    @ElementCollection
    @CollectionTable(name = "webtoon_genre", joinColumns = [JoinColumn(name = "webtoon_id")])
    @Column(name = "genre")
    var genres: MutableList<String> = genres as MutableList<String>

    @Column(name = "background_color")
    var backgroudColor: String = backgroundColor

    @Column(name = "isComplete")
    var isComplete: Boolean? = isComplete

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

    fun update(request: WebtoonUpsertRequest): Webtoon {
        site = request.site
        title = request.title
        authors = request.authors.toMutableList()
        weekdays = request.dayOfWeeks.toMutableList()
        popularity = request.popularity
        thumbnail = request.thumbnail
        summary = request.summary
        genres = request.genres.toMutableList()
        url = request.url
        score = request.score
        backgroudColor = request.backgroundColor
        isComplete = request.isComplete
        return this
    }
}
