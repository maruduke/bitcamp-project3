package com.example.backend.entity.maria;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaskProgress is a Querydsl query type for TaskProgress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTaskProgress extends EntityPathBase<TaskProgress> {

    private static final long serialVersionUID = -736179126L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaskProgress taskProgress = new QTaskProgress("taskProgress");

    public final StringPath documentId = createString("documentId");

    public final NumberPath<Long> refId = createNumber("refId", Long.class);

    public final NumberPath<Long> refUserId = createNumber("refUserId", Long.class);

    public final EnumPath<com.example.backend.entity.maria.enumData.DocState> state = createEnum("state", com.example.backend.entity.maria.enumData.DocState.class);

    public final QUser user;

    public QTaskProgress(String variable) {
        this(TaskProgress.class, forVariable(variable), INITS);
    }

    public QTaskProgress(Path<? extends TaskProgress> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTaskProgress(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTaskProgress(PathMetadata metadata, PathInits inits) {
        this(TaskProgress.class, metadata, inits);
    }

    public QTaskProgress(Class<? extends TaskProgress> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

