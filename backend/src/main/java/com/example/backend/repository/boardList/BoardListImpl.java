package com.example.backend.repository.boardList;

import com.example.backend.dto.board.MyListDto;
import com.example.backend.dto.board.WaitDto;
import com.example.backend.entity.maria.*;
import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.maria.enumData.DocType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@PersistenceContext
@Log4j2
public class BoardListImpl extends QuerydslRepositorySupport implements BoardList {

    private final EntityManager entityManager;

    public BoardListImpl(EntityManager entityManager){
        super(Document.class);
        this.entityManager = entityManager;
    }

    @Override
    public Slice<MyListDto> findByWriter(User user, Pageable pageable) {
        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        QDocument document = QDocument.document;
        QTaskProgress taskProgress = QTaskProgress.taskProgress;

        int pageSize = pageable.getPageSize();

        List<MyListDto> content = query
                .select(Projections.constructor(MyListDto.class,
                        document.documentId,
                        document.title,
                        document.type,
                        document.state,
                        document.createDate))
                .from(document)
                .leftJoin(taskProgress).on(document.documentId.eq(taskProgress.documentId))
                .where(document.writer.eq(user.getUserId())
                        .and(document.state.eq(taskProgress.state))
                        .and(document.writer.eq(taskProgress.refUserId)))
                .orderBy(document.createDate.desc(), taskProgress.refId.desc())
                .offset(pageable.getOffset())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = false;
        if(content.size() > pageSize){
            content.remove(pageSize);
            hasNext = true;
        }

        // Slice 객체 변환
        Slice<MyListDto> result = new SliceImpl<>(content, pageable, hasNext);

        return result;
    }

    @Override
    public Slice<WaitDto> findByStateIn(User user, Pageable pageable, List<DocState> stateList) {
        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        QDocument document = QDocument.document;
        QTaskProgress taskProgress = QTaskProgress.taskProgress;
        QUser qUser = QUser.user;

        int pageSize = pageable.getPageSize();

        List<WaitDto> content = query
                .select(Projections.constructor(WaitDto.class,
                        document.documentId,
                        qUser.name,
                        document.type,
                        document.state,
                        document.createDate))
                .from(document)
                .leftJoin(taskProgress).on(document.documentId.eq(taskProgress.documentId))
                .leftJoin(qUser).on(document.writer.eq(qUser.userId))
                .where(taskProgress.refUserId.eq(user.getUserId())
                        .and(document.state.in(stateList))
                        .and(taskProgress.refUserId.ne(document.writer)))
                .orderBy(document.createDate.desc(), taskProgress.refId.desc())
                .offset(pageable.getOffset())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = false;
        if(content.size() > pageSize){
            content.remove(pageSize);
            hasNext = true;
        }

        // Slice 객체 변환
        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<WaitDto> findByState(User user, Pageable pageable, List<DocState> stateList) {
        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        QDocument document = QDocument.document;
        QRef ref = QRef.ref;
        QUser qUser = QUser.user;

        int pageSize = pageable.getPageSize();

        List<WaitDto> content = query
                .select(Projections.constructor(WaitDto.class,
                        document.documentId,
                        qUser.name,
                        document.type,
                        document.state,
                        document.createDate))
                .from(document)
                .leftJoin(ref).on(document.documentId.eq(ref.documentId))
                .leftJoin(qUser).on(document.writer.eq(qUser.userId))
                .where(ref.refUserId.eq(user.getUserId())
                        .and(document.state.in(stateList)))
//                        .and(ref.refUserId.ne(document.writer)))
                .orderBy(document.createDate.desc(), ref.documentId.desc())
                .offset(pageable.getOffset())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = false;
        if(content.size() > pageSize){
            content.remove(pageSize);
            hasNext = true;
        }

        // Slice 객체 변환
        return new SliceImpl<>(content, pageable, hasNext);
    }

}