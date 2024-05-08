package com.example.backend.repository.mylist;

import com.example.backend.dto.board.MyListDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.entity.maria.QDocument;
import com.example.backend.entity.maria.QTaskProgress;
import com.example.backend.entity.maria.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@PersistenceContext
@Log4j2
public class MyListImpl extends QuerydslRepositorySupport implements MyList {

    private final EntityManager entityManager;

    public MyListImpl(EntityManager entityManager){
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
                        document.title,
                        document.type,
                        document.state,
                        document.createDate))
                .from(document)
                .leftJoin(taskProgress).on(document.documentId.eq(taskProgress.documentId))
                .where(document.writer.eq(user.getUserId()))
                .where(taskProgress.refUserId.eq(user.getUserId()))
                .offset(pageable.getOffset())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = false;
        if(content.size() >= pageSize){
            content.remove(pageSize);
            hasNext = true;
        }

        // Slice 객체 변환
        Slice<MyListDto> result = new SliceImpl<>(content, pageable, hasNext);

        System.out.println(pageable);
        return result;
    }
}
