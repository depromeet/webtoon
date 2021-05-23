package com.depromeet.webtoon.core.domain.comment.repository

import com.depromeet.webtoon.core.domain.comment.model.Comment
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.webtoon.id = :webtoonId and c.account.id = :accountId")
    fun findByWebtoonIdAndAccountId(webtoonId: Long, accountId: Long): Comment?

    @Query("select c from Comment c where c.webtoon.id = :webtoonId")
    fun findAllByWebtoonId(webtoonId: Long): List<Comment>?

    fun findTop5ByWebtoonOrderByCreatedAtDesc(webtoon: Webtoon): List<Comment>?
}
