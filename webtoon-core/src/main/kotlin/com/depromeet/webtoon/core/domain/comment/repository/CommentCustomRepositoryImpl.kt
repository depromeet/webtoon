package com.depromeet.webtoon.core.domain.comment.repository

import com.depromeet.webtoon.core.domain.comment.model.Comment
import com.depromeet.webtoon.core.domain.comment.model.QComment.comment
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
class CommentCustomRepositoryImpl(@Autowired private val entityManger: EntityManager): CommentCustomRepository, QuerydslRepositorySupport(Comment::class.java) {

    private val query = JPAQueryFactory(entityManger)


    override fun getComments(webtoonId: Long, commentId: Long?, pageSize: Long): List<Comment> {
        val dynamicLtId = BooleanBuilder()

        if (commentId != null) {
            dynamicLtId.and(comment.id.lt(commentId))
        }

        return query
            .select(comment)
            .from(comment)
            .where(dynamicLtId.and(comment.webtoon.id.eq(webtoonId)))
            .orderBy(comment.id.desc())
            .limit(pageSize)
            .fetch()
    }


}
