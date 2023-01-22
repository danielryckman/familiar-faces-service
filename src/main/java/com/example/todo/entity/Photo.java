package com.example.todo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.example.todo.entity.User;
import com.example.todo.entity.Familymember;
import com.example.todo.entity.Task;
import com.example.todo.entity.Record;

@Entity
@Data
public class Photo {

    @Id
    @GeneratedValue
    @Column
    @NotNull(message="{NotNull.Photo.qid}")
    private Long id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String description;
    
    @Column(nullable = true)
    private long datetoshow;
    
    @Column(nullable = true)
    private long datelastviewed;
    
    @Column(nullable = true)
    private long datecreated;
    
    @Column(nullable = true)
    private String comment;
    
    //training photos, created by user, created automatically by tasks etc
    @Column(nullable = true)
    private String ptype;
    
    @Lob
    @Column(length = 1000000)
    private String image;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familymember", referencedColumnName = "id", nullable=true)
    private Familymember familymember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myuser", referencedColumnName = "id", nullable=true)
    private User myuser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task", referencedColumnName = "id", nullable=true)
    private Task task;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myrecord", referencedColumnName = "id", nullable=true)
    private Record myrecord;
    
}
