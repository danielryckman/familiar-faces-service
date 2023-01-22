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
    
    @Column
    @NotNull(message="{NotNull.Familymember.dob}")
    private String dob;

    @Column
    @NotNull(message="{NotNull.Question.gender}")
    private String gender;
    
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
        
    public void setMyuser(User user){
    	myuser =user;
    }
}
