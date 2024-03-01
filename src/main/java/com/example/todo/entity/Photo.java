package com.example.todo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
//import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.example.todo.entity.User;
import com.example.todo.entity.Familymember;
import com.example.todo.entity.Task;
import com.example.todo.entity.Record;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private String title;
    
    @Column(nullable = true)
    private String personinpic;
    
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
    
    @Column(nullable = true)
    private String uploaddir;
    
    @Lob
    @Column(length = 1000000)
    private String image;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familymember", referencedColumnName = "id", nullable=true)
    private Familymember familymember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myuser", referencedColumnName = "id", nullable=true)
    @JsonIgnoreProperties("photo")
    //@EqualsAndHashCode.Exclude
    //@ToString.Exclude
    private User myuser;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "task", referencedColumnName = "id", nullable=true, insertable=true, updatable=false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myrecord", referencedColumnName = "id", nullable=true)
    private Record myrecord;
    
    @JsonBackReference
    public User getMyuser(){
    	return myuser;
    }
    
    @JsonBackReference
    public Record getMyrecord(){
    	return myrecord;
    }
    
    @JsonBackReference
    public Task getTask(){
    	return task;
    }
    
    @JsonBackReference
    public Familymember getFamilymember(){
    	return familymember;
    }
}
