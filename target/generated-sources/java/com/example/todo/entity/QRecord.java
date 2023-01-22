package com.example.todo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecord is a Querydsl query type for Record
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecord extends EntityPathBase<Record> {

    private static final long serialVersionUID = 1804497093L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecord record = new QRecord("record");

    public final NumberPath<Long> apptime = createNumber("apptime", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser myuser;

    public final SetPath<Photo, QPhoto> photo = this.<Photo, QPhoto>createSet("photo", Photo.class, QPhoto.class, PathInits.DIRECT2);

    public final NumberPath<Long> phototime = createNumber("phototime", Long.class);

    public final StringPath rdate = createString("rdate");

    public final SetPath<Test, QTest> tests = this.<Test, QTest>createSet("tests", Test.class, QTest.class, PathInits.DIRECT2);

    public final NumberPath<Long> testtime = createNumber("testtime", Long.class);

    public QRecord(String variable) {
        this(Record.class, forVariable(variable), INITS);
    }

    public QRecord(Path<? extends Record> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecord(PathMetadata metadata, PathInits inits) {
        this(Record.class, metadata, inits);
    }

    public QRecord(Class<? extends Record> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.myuser = inits.isInitialized("myuser") ? new QUser(forProperty("myuser")) : null;
    }

}

