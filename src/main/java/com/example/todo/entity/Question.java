package com.example.todo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
public class Question {
	
    @Id
    @GeneratedValue
    @Column
    @NotNull(message="{NotNull.Question.qid}")
    private Long qid;
    
    @Column(nullable = true)
    private String testname;
    
    @Column(nullable = true)
    private String hint;

    @Column
    @NotNull(message="{NotNull.Question.description}")
    private String description;
    
    @Column(nullable = true)
    private String solution;
    
    @Column(nullable = true)
    private String answer;
    
    @Column
    @NotNull(message="{NotNull.Question.category}")
    private String category;
        
    @Column(nullable = true)
    private Long timetoanswer;
    
    @Lob
    @Column(length = 100000, nullable = true)
    private String  image;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test", referencedColumnName = "id", nullable=true)
    private Test test;
    
    @Column(nullable = true)
    private String score;

    public Question(){}
    
	public Question(String name, String hint, String description, String solution, String  category, String image, Test test){
		 this.testname=name;
		 this.hint=hint;
		 this.description=description;
		 this.solution=solution;
		 this.category=category;
		 this.image=image;
		 this.test=test;
	}
	
	public Question(String name, String hint, String description, String solution, String  category, String image){
		 this.testname=name;
		 this.hint=hint;
		 this.description=description;
		 this.solution=solution;
		 this.category=category;
		 this.image=image;
	}	
	
    @JsonBackReference
	public Test getTest(){
		return test;
	}

}
