package com.example.todo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTask is a Querydsl query type for Task
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTask extends EntityPathBase<Task> {

    private static final long serialVersionUID = -1294153991L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTask task = new QTask("task");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final QUser myuser;

    public final StringPath name = createString("name");

    public final SetPath<Photo, QPhoto> photos = this.<Photo, QPhoto>createSet("photos", Photo.class, QPhoto.class, PathInits.DIRECT2);

    public final NumberPath<Integer> repeat = createNumber("repeat", Integer.class);

    public final NumberPath<Long> repeatend = createNumber("repeatend", Long.class);

    public final NumberPath<Long> repeatstart = createNumber("repeatstart", Long.class);

    public final NumberPath<Long> schedule = createNumber("schedule", Long.class);

    public final StringPath url = createString("url");

    public QTask(String variable) {
        this(Task.class, forVariable(variable), INITS);
    }

    public QTask(Path<? extends Task> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTask(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTask(PathMetadata metadata, PathInits inits) {
        this(Task.class, metadata, inits);
    }

    public QTask(Class<? extends Task> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.myuser = inits.isInitialized("myuser") ? new QUser(forProperty("myuser")) : null;
    }

}

