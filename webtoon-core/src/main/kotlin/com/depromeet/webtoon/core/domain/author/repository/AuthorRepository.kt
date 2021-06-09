package com.depromeet.webtoon.core.domain.author.repository

import com.depromeet.webtoon.core.domain.author.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {
    fun findAllByNameIn(names: List<String>): List<Author>

    @Query(
        """
            select * from author a,
            order by rand()
            limit 20
        """,
        nativeQuery = true
    )
    fun find20RandomAuthors(): List<Author>
}
