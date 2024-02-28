package com.example.todo.dto;

import lombok.Data;
import com.example.todo.dto.TestDTO;
import com.example.todo.dto.RecordDTO;
import com.example.todo.dto.UserDTO;
import com.example.todo.dto.PhotoDTO;
import java.util.Set;

@Data
public class FamilymemberDTO {

    private long id;
    private String firstname;
    private String lastname;
    private String nickname;
    private String email;
    private String password;
    private String description;
    private int isadmin;
    private String relationship;
    private String dob;
    private String gender;
    private String hobbies;
    private String songs;
    private Set<PhotoDTO> photo;
    private UserDTO user;
    private long userid;
    public FamilymemberDTO(Long id, String firstname, String lastname, String dob, String nickname, String hobbies, String gender, String email, String password, String description, String relationship, int isadmin, long userid){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.nickname = nickname;
        this.hobbies = hobbies;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.description = description;
        this.relationship = relationship;
        this.isadmin = isadmin;
        this.userid = userid;
    }    
}
