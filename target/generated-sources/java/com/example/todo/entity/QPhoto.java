package com.example.todo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhoto is a Querydsl query type for Photo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPhoto extends EntityPathBase<Photo> {

    private static final long serialVersionUID = -1467557058L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhoto photo = new QPhoto("photo");

    public final StringPath comment = createString("comment");

    public final NumberPath<Long> datecreated = createNumber("datecreated", Long.class);

    public final NumberPath<Long> datelastviewed = createNumber("datelastviewed", Long.class);

    public final NumberPath<Long> datetoshow = createNumber("datetoshow", Long.class);

    public final StringPath description = createString("description");

    public final QFamilymember familymember;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final QRecord myrecord;

    public final QUser myuser;

    public final StringPath name = createString("name");

    public final StringPath personinpic = createString("personinpic");

    public final StringPath ptype = createString("ptype");

    public final QTask task;

    public final StringPath title = createString("title");

    public QPhoto(String variable) {
        this(Photo.class, forVariable(variable), INITS);
    }

    public QPhoto(Path<? extends Photo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhoto(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhoto(PathMetadata metadata, PathInits inits) {
        this(Photo.class, metadata, inits);
    }

    public QPhoto(Class<? extends Photo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.familymember = inits.isInitialized("familymember") ? new QFamilymember(forProperty("familymember"), inits.get("familymember")) : null;
        this.myrecord = inits.isInitialized("myrecord") ? new QRecord(forProperty("myrecord"), inits.get("myrecord")) : null;
        this.myuser = inits.isInitialized("myuser") ? new QUser(forProperty("myuser")) : null;
        this.task = inits.isInitialized("task") ? new QTask(forProperty("task"), inits.get("task")) : null;
    }

}

