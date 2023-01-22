package com.example.todo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTest is a Querydsl query type for Test
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTest extends EntityPathBase<Test> {

    private static final long serialVersionUID = -1294150138L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTest test = new QTest("test");

    public final NumberPath<Long> endtime = createNumber("endtime", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRecord myrecord;

    public final QUser myuser;

    public final StringPath name = createString("name");

    public final SetPath<Question, QQuestion> question = this.<Question, QQuestion>createSet("question", Question.class, QQuestion.class, PathInits.DIRECT2);

    public final StringPath score = createString("score");

    public final NumberPath<Long> starttime = createNumber("starttime", Long.class);

    public final StringPath subscores = createString("subscores");

    public QTest(String variable) {
        this(Test.class, forVariable(variable), INITS);
    }

    public QTest(Path<? extends Test> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTest(PathMetadata metadata, PathInits inits) {
        this(Test.class, metadata, inits);
    }

    public QTest(Class<? extends Test> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.myrecord = inits.isInitialized("myrecord") ? new QRecord(forProperty("myrecord"), inits.get("myrecord")) : null;
        this.myuser = inits.isInitialized("myuser") ? new QUser(forProperty("myuser")) : null;
    }

}

