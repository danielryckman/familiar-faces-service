package com.example.todo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue
    @Column
    @NotNull(message="{NotNull.Familymember.id}")
    private Long id;

    @Column
    @NotNull(message="{NotNull.Task.name}")
    private String name;

    @Column(nullable = true)
    private String description;
    
    @Column(nullable = true)
    private String url;
    
    @Column
    @NotNull(message="{NotNull.Task.schedule}")
    private long schedule;
    
    @Column(nullable = true)
    private int repeat;
    
    @Column(nullable = true)
    private long repeatstart;
    
    @Column(nullable = true)
    private long repeatend;
    
    @Lob
    @Column(length = 1000000)
    private String image;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myuser", referencedColumnName = "id", nullable=true)
    private User myuser;


}
