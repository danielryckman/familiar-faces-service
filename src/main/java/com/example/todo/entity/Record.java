package com.example.todo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import com.example.todo.entity.User;
import com.example.todo.entity.Test;
import com.example.todo.entity.Photo;
import java.util.HashSet;

@Entity
@Getter
@Setter
@Table(name = "records")
public class Record {
	
    @Id
    @GeneratedValue
    @Column
    @NotNull(message="{NotNull.Record.id}")
    private Long id;
    
    @NotNull(message="{NotNull.Record.rdate}")
    private String rdate;
    
    @Column(nullable = true)
    private long apptime;

    @Column(nullable = true)
    private long phototime;
    
    @Column(nullable = true)
    private long testtime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myuser", referencedColumnName = "id", nullable=true)
    private User myuser;
    
    @Column
    @OneToMany(mappedBy="myrecord",cascade = CascadeType.ALL)
    private Set<Photo> photo=new HashSet<>();
    
    @Column
    @OneToMany(mappedBy="myrecord",cascade = CascadeType.ALL)
    private Set<Test> tests=new HashSet<>();
    
}
