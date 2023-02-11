package com.example.todo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamilymember is a Querydsl query type for Familymember
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFamilymember extends EntityPathBase<Familymember> {

    private static final long serialVersionUID = -2132694926L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFamilymember familymember = new QFamilymember("familymember");

    public final StringPath description = createString("description");

    public final StringPath dob = createString("dob");

    public final StringPath email = createString("email");

    public final StringPath firstname = createString("firstname");

    public final StringPath gender = createString("gender");

    public final StringPath hobbies = createString("hobbies");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isAdmin = createNumber("isAdmin", Integer.class);

    public final StringPath lastname = createString("lastname");

    public final QUser myuser;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final SetPath<Photo, QPhoto> photo = this.<Photo, QPhoto>createSet("photo", Photo.class, QPhoto.class, PathInits.DIRECT2);

    public final StringPath relationship = createString("relationship");

    public final NumberPath<Long> userid = createNumber("userid", Long.class);

    public QFamilymember(String variable) {
        this(Familymember.class, forVariable(variable), INITS);
    }

    public QFamilymember(Path<? extends Familymember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFamilymember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFamilymember(PathMetadata metadata, PathInits inits) {
        this(Familymember.class, metadata, inits);
    }

    public QFamilymember(Class<? extends Familymember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.myuser = inits.isInitialized("myuser") ? new QUser(forProperty("myuser")) : null;
    }

}

