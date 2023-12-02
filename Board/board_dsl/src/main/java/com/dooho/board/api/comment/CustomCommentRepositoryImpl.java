package com.dooho.board.api.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomCommentRepositoryImpl implements CustomCommentRepository{

    @PersistenceContext
    EntityManager em;

    private JPAQueryFactory queryFactory;
    private final QCommentEntity qCommentEntity = QCommentEntity.commentEntity;

    public CustomCommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CommentEntity> getComment(Integer boardNumber) {
        return queryFactory
                .selectFrom(qCommentEntity)
                .where(qCommentEntity.boardNumber.eq(boardNumber))
                .orderBy(qCommentEntity.commentWriteDate.desc())
                .fetch();
    }
}
