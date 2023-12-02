package com.dooho.board.api.board;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CustomBoardRepositoryImpl implements CustomBoardRepository{

    @PersistenceContext
    EntityManager em;

    private JPAQueryFactory queryFactory;
    private final QBoardEntity qBoardEntity = QBoardEntity.boardEntity;

    public CustomBoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<BoardEntity> findTop3(LocalDate boardWriteDate) {
        return queryFactory
                .selectFrom(qBoardEntity)
                .where(qBoardEntity.boardWriteDate.after(boardWriteDate))
                .orderBy(qBoardEntity.boardLikeCount.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public List<BoardEntity> findList(){
        return queryFactory
                .selectFrom(qBoardEntity)
                .orderBy(qBoardEntity.boardWriteDate.desc())
                .orderBy(qBoardEntity.boardNumber.desc())
                .fetch();
    }



}
