package com.example.backend.entity.maria;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNote is a Querydsl query type for Note
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNote extends EntityPathBase<Message> {

    private static final long serialVersionUID = -1570935446L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNote note = new QNote("note");

    public final StringPath message = createString("message");

    public final NumberPath<Long> noteId = createNumber("noteId", Long.class);

    public final BooleanPath readCheck = createBoolean("readCheck");

    public final BooleanPath receiverDelete = createBoolean("receiverDelete");

    public final QUser receiverId;

    public final BooleanPath senderDelete = createBoolean("senderDelete");

    public final QUser senderId;

    public final DateTimePath<java.time.LocalDateTime> sendTime = createDateTime("sendTime", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public QNote(String variable) {
        this(Message.class, forVariable(variable), INITS);
    }

    public QNote(Path<? extends Message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNote(PathMetadata metadata, PathInits inits) {
        this(Message.class, metadata, inits);
    }

    public QNote(Class<? extends Message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiverId = inits.isInitialized("receiverId") ? new QUser(forProperty("receiverId")) : null;
        this.senderId = inits.isInitialized("senderId") ? new QUser(forProperty("senderId")) : null;
    }

}

