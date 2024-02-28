package com.example.todo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import com.example.todo.entity.User;
import com.example.todo.entity.Photo;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Getter
@Setter
public class Familymember {
	
    @Id
    @GeneratedValue
    @Column
    @NotNull(message="{NotNull.Familymember.id}")
    private long id;
    
    @Column(nullable = false)
    @NotNull(message="{NotNull.Familymember.firstname}")
    private String firstname;
    
    @Column(nullable = false)
    @NotNull(message="{NotNull.Familymember.lastname}")
    private String lastname; 
    
    @Column(nullable = false)
    @NotNull(message="{NotNull.Familymember.email}")
    private String email; 
    
    @Column(nullable = false)
    @NotNull(message="{NotNull.Familymember.password}")
    private String password; 
    
    @Column(nullable = true)
    private String description;

    @Column
    @NotNull(message="{NotNull.Familymember.dob}")
    private String dob;

    @Column
    @NotNull(message="{NotNull.Question.gender}")
    private String gender;
    
    @Column
    @NotNull(message="{NotNull.Question.userid}")
    private long userid;
    
    @Column(nullable = true)
    private int isAdmin;
    
    @Column(nullable = true)
    private String nickname;
    
    @Column(nullable = true)
    private String hobbies; 
    
    @Column(nullable = true)
    private String relationship; 
       
    @Column
    @OneToMany(mappedBy="familymember",cascade = CascadeType.ALL)
    private Set<Photo> photo=new HashSet<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myuser", referencedColumnName = "id", nullable=true)
    private User myuser;
        
    @JsonBackReference
    public User getMyuser(){
    	return myuser;
    }
    
    public void setMyuser(User user){
    	myuser =user;
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

    public long getId(){
    	return id;
    }
    
    public long getUserId(){
    	return userid;
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
    
    public String getDescription(){
    	return description;
    }
    
    public String getRelationship(){
    	return relationship;
    }
    
    public int getIsAdmin(){
    	return isAdmin;
    }
   
}
