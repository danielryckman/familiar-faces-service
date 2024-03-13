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
import com.example.todo.entity.Familymember;
import com.example.todo.entity.Test;
import com.example.todo.entity.Photo;
import com.example.todo.entity.Record;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
   
    @Column(nullable = false)
    @NotNull(message="{NotNull.User.firstname}")
    private String firstname;
    
    @Column(nullable = false)
    @NotNull(message="{NotNull.User.lastname}")
    private String lastname; 
    
    @Column(nullable = false)
    @NotNull(message="{NotNull.User.email}")
    private String email; 
    
    @Column(nullable = false)
    @NotNull(message="{NotNull.Familymember.password}")
    private String password; 
    
    @Column(nullable = true)
    private String description;
    
    @Column
    @NotNull(message="{NotNull.User.dob}")
    private String dob;

    @Column
    @NotNull(message="{NotNull.User.gender}")
    private String gender;
    
    @Column(nullable = true)
    private String nickname;
    
    @Column(nullable = true)
    private String hobbies; 
       
    @Column(nullable = true)
    private String songs;

    @Column(nullable = true)
    private String authtoken;

    @Column(nullable = true)
    private Long lastused;
    
    @Column
    //@OneToMany(mappedBy="myuser",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OneToMany(mappedBy="myuser",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("myuser")
    private Set<Photo> photo=new HashSet<>();     
    
    @Column
    @OneToMany(mappedBy="myuser",cascade = CascadeType.ALL)
    private Set<Familymember> familymembers = new HashSet<>();
    
    @Column
    @OneToMany(mappedBy="myuser",cascade = CascadeType.ALL)
    private Set<Test> tests = new HashSet<>();
    
    @Column
    @OneToMany(mappedBy="myuser",cascade = CascadeType.ALL)
    private Set<Record> records = new HashSet<>();
    
    @Column
    @OneToMany(mappedBy="myuser",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("myuser")
    private Set<Task> tasks = new HashSet<>();
    
    @JsonManagedReference
    public Set<Familymember> getFamilymembers(){
    	return familymembers;
    }
    
    @JsonManagedReference
    public Set<Task> getTasks(){
    	return tasks;
    }

    @JsonManagedReference
    public Set<Photo> getPhoto(){
    	return photo;
    }
    
    public String getFirstname(){
    	return firstname;
    }
    
    public String getLastname(){
    	return lastname;
    }

    public String getHobbies(){
        return hobbies;
    }

    public Long getId(){
    	return id;
    }

    public String getDob(){
    	return dob;
    }

    public String getNickname(){
    	return nickname;
    }

   public String getEmail(){
    	return email;
    }

    public String getPassword(){
    	return password;
    }

    public void setPassword(String newPassword){
        password = newPassword;
    }

    public String getAuthtoken(){
        return authtoken;
    }

    public void setAuthtoken(String newauthtoken){
        authtoken = newauthtoken;
    }

    public long getLastused(){
        return lastused;
    }

    public void setLastused(long newlastused){
        lastused = newlastused;
    }
    
    
}
