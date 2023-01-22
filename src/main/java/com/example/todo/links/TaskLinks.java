package com.example.todo.links;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class TaskLinks {

    private final EntityLinks entityLinks;

    public static final String TASKS = "/tasks";
    public static final String TASK = "/task/{id}";
    public static final String CREATE_TASK = "/task";
    public static final String CREATE_TASK_USER = "/taskuser";
    public static final String QUERY_TASK = "/tasks/query";
    public static final String DELETE_RANGE = "/tasks/deleterange";
    public static final String TESTS = "/tests";
    public static final String TEST = "/test";
    public static final String USERS = "/users";
    public static final String USER = "/user";
    public static final String PHOTOS = "/photos";
    public static final String PHOTO = "/photo";
    public static final String PHOTO_USER = "/photouser";
    public static final String PHOTO_RANGE = "/photorange";
    public static final String RECORDS = "/records";
    public static final String RECORD = "/record";
    public static final String RECORD_USER = "/recorduser";
    public static final String FAMILYMEMBERS = "/familymembers";
    public static final String FAMILYMEMBER = "/familymember";

    public TaskLinks(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    /*public Link getCancelLink(Event event) {
        return entityLinks.linkForSingleResource(event).slash(CANCEL_EVENT).withRel(CANCEL_REL);
    }*/
}
