package com.depromeet.webtoon.core.domain.comment.repository

import com.depromeet.webtoon.core.domain.comment.model.Comment
import com.depromeet.webtoon.core.domain.webtoon.model.Webtoon
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long>, CommentCustomRepository {
    
}
