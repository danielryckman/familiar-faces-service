package com.example.todo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Set;
import java.util.HashSet;
import com.example.todo.entity.User;
import com.example.todo.entity.Photo;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue
    @Column
    @NotNull(message="{NotNull.Task.id}")
    private Long id;

    @Column
    @NotNull(message="{NotNull.Task.name}")
    private String name;

    @Column(nullable = true)
    private String description;
    
    @Column(nullable = true)
    private String prompt;
    
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
    
    @Column
    @OneToMany(mappedBy="task",cascade = CascadeType.ALL)
    private Set<Photo> photos = new HashSet<>();

    @JsonBackReference
    public User getMyuser(){
    	return myuser;
    }
    
    @JsonManagedReference
    public Set<Photo> getPhotos(){
    	return photos;
    }
}
