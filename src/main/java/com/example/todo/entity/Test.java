package com.example.todo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.example.todo.entity.Question;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
public class Test {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    //@NotNull(message="{NotNull.Test.id}")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String name;
    
    @Column(nullable = true)
    private Long starttime;

    @Column(nullable = true)
    private Long endtime;
    
    @Column
    @OneToMany(mappedBy="test",cascade = CascadeType.ALL)
    private Set<Question> question=new HashSet<>();
    
    @Column(nullable = true)
    private String score;
    
    @Column(nullable = true)
    private String subscores;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myrecord", referencedColumnName = "id", nullable=true)
    private Record myrecord;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myuser", referencedColumnName = "id", nullable=true)
    private User myuser;
    
    public void setId(long testid){
    	id=testid;
    }
    
    public void setQuestion(List<Question> questionList){
    	question = new HashSet<Question>(questionList);
    }
    
    @JsonBackReference
    public User getMyuser(){
    	return myuser;
    }
    
    @JsonManagedReference
    public Set<Question> getQuestion(){
    	return question;
    }
}
