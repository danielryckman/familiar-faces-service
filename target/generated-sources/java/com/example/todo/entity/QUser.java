package com.example.todo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1294107329L;

    public static final QUser user = new QUser("user");

    public final StringPath description = createString("description");

    public final StringPath dob = createString("dob");

    public final StringPath email = createString("email");

    public final SetPath<Familymember, QFamilymember> familymembers = this.<Familymember, QFamilymember>createSet("familymembers", Familymember.class, QFamilymember.class, PathInits.DIRECT2);

    public final StringPath firstname = createString("firstname");

    public final StringPath gender = createString("gender");

    public final StringPath hobbies = createString("hobbies");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastname = createString("lastname");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final SetPath<Photo, QPhoto> photo = this.<Photo, QPhoto>createSet("photo", Photo.class, QPhoto.class, PathInits.DIRECT2);

    public final SetPath<Record, QRecord> records = this.<Record, QRecord>createSet("records", Record.class, QRecord.class, PathInits.DIRECT2);

    public final StringPath songs = createString("songs");

    public final SetPath<Task, QTask> tasks = this.<Task, QTask>createSet("tasks", Task.class, QTask.class, PathInits.DIRECT2);

    public final SetPath<Test, QTest> tests = this.<Test, QTest>createSet("tests", Test.class, QTest.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

