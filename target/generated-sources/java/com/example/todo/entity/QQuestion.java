package com.example.todo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = -1411714214L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestion question = new QQuestion("question");

    public final StringPath answer = createString("answer");

    public final StringPath category = createString("category");

    public final StringPath description = createString("description");

    public final StringPath hint = createString("hint");

    public final StringPath image = createString("image");

    public final NumberPath<Long> qid = createNumber("qid", Long.class);

    public final StringPath score = createString("score");

    public final StringPath solution = createString("solution");

    public final QTest test;

    public final StringPath testname = createString("testname");

    public final NumberPath<Long> timetoanswer = createNumber("timetoanswer", Long.class);

    public QQuestion(String variable) {
        this(Question.class, forVariable(variable), INITS);
    }

    public QQuestion(Path<? extends Question> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestion(PathMetadata metadata, PathInits inits) {
        this(Question.class, metadata, inits);
    }

    public QQuestion(Class<? extends Question> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.test = inits.isInitialized("test") ? new QTest(forProperty("test"), inits.get("test")) : null;
    }

}

