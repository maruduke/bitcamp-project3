package com.example.backend.repository;

import com.example.backend.dto.board.MyListDto;
import com.example.backend.entity.maria.QDocument;
import com.example.backend.entity.maria.QTaskProgress;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

@SpringBootTest
public class DocumentRepositoryTest {

    @Autowired
    EntityManager entityManager;

    JPAQueryFactory query;

    @BeforeEach
    public void setUp() {
        query = new JPAQueryFactory(entityManager);

    }

    @Test
    public void list(){
        QDocument document = QDocument.document;
        QTaskProgress taskProgress = QTaskProgress.taskProgress;

        List<Tuple> results = query.select(document.title, document.state, document.type, document.createDate, taskProgress.ref_user_id)
                .from(document, taskProgress)
                .leftJoin(document).on(taskProgress.documentId.eq(document.documentId))
                .fetch();

        for(Tuple tuple : results) {
            System.out.println(tuple);
        }
    }

    @Test
    public void myList(){
        QDocument document = QDocument.document;
        QTaskProgress taskProgress = QTaskProgress.taskProgress;

        Pageable pageable = PageRequest.of(0, 10);
        int pageSize = pageable.getPageSize();

        List<MyListDto> content = query
                .select(Projections.constructor(MyListDto.class,
                        document.title,
                        document.type,
                        document.state,
                        document.createDate))
                .from(document)
                .leftJoin(taskProgress).on(taskProgress.documentId.eq(document.documentId))
                .where(taskProgress.ref_user_id.eq(3L))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        System.out.println("=======================================================");
        content.forEach(System.out::println);
        System.out.println("=======================================================");
    }
}
