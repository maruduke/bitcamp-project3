package com.example.backend.entity.maria;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRef is a Querydsl query type for Ref
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRef extends EntityPathBase<Ref> {

    private static final long serialVersionUID = -881955813L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRef ref = new QRef("ref");

    public final StringPath documentId = createString("documentId");

    public final QUser ref_user_id;

    public final NumberPath<Long> refId = createNumber("refId", Long.class);

    public final NumberPath<Long> refUserId = createNumber("refUserId", Long.class);

    public final EnumPath<com.example.backend.entity.maria.enumData.DocState> state = createEnum("state", com.example.backend.entity.maria.enumData.DocState.class);

    public QRef(String variable) {
        this(Ref.class, forVariable(variable), INITS);
    }

    public QRef(Path<? extends Ref> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRef(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRef(PathMetadata metadata, PathInits inits) {
        this(Ref.class, metadata, inits);
    }

    public QRef(Class<? extends Ref> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ref_user_id = inits.isInitialized("ref_user_id") ? new QUser(forProperty("ref_user_id")) : null;
    }

}

