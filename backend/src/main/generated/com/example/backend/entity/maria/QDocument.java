package com.example.backend.entity.maria;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDocument is a Querydsl query type for Document
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocument extends EntityPathBase<Document> {

    private static final long serialVersionUID = 117117587L;

    public static final QDocument document = new QDocument("document");

    public final DatePath<java.time.LocalDate> createDate = createDate("createDate", java.time.LocalDate.class);

    public final StringPath documentId = createString("documentId");

    public final EnumPath<com.example.backend.entity.maria.enumData.DocState> state = createEnum("state", com.example.backend.entity.maria.enumData.DocState.class);

    public final StringPath title = createString("title");

    public final EnumPath<com.example.backend.entity.maria.enumData.DocType> type = createEnum("type", com.example.backend.entity.maria.enumData.DocType.class);

    public final NumberPath<Long> writer = createNumber("writer", Long.class);

    public QDocument(String variable) {
        super(Document.class, forVariable(variable));
    }

    public QDocument(Path<? extends Document> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDocument(PathMetadata metadata) {
        super(Document.class, metadata);
    }

}

