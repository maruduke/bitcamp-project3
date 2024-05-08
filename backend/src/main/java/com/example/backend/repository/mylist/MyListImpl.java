package com.example.backend.repository.mylist;

import com.example.backend.dto.board.MyListDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.entity.maria.QDocument;
import com.example.backend.entity.maria.QTaskProgress;
import com.example.backend.entity.maria.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


public class MyListImpl extends QuerydslRepositorySupport implements MyList {
    EntityManager entityManager;
    JPAQueryFactory query = new JPAQueryFactory(entityManager);

    public MyListImpl(){
        super(Document.class);
    }

    @Override
    public Slice<MyListDto> findSliceBy(User user, Pageable pageable) {

        QDocument document = QDocument.document;
        QTaskProgress taskProgress = QTaskProgress.taskProgress;

        int pageSize = pageable.getPageSize();

        List<MyListDto> content = query
                .select(Projections.constructor(MyListDto.class,
                        document.title,
                        document.type,
                        document.state,
                        document.createDate))
                .from(document)
                .leftJoin(taskProgress).on(taskProgress.documentId.eq(document.documentId))
                .where(taskProgress.refUserId.eq(user.getUserId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
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
}
