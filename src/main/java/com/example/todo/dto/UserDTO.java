package com.example.todo.dto;

import lombok.Data;
import com.example.todo.dto.TestDTO;
import com.example.todo.dto.RecordDTO;
import com.example.todo.dto.PhotoDTO;
import com.example.todo.dto.TaskDTO;
import com.example.todo.dto.FamilymemberDTO;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String nickname;
    private String email;
    private String password;
    private String description;
    private String dob;
    private String gender;
    private String hobbies;
    private String relationship;
    private String authtoken;
    private Set<PhotoDTO> photo;
    private Set<FamilymemberDTO> familymembers;
    private Set<RecordDTO> records;
    private Set<TaskDTO> tasks;
    private Set<TestDTO> tests;
    public UserDTO(Long id, String firstname, String lastname, String dob, String nickname, String hobbies, String gender, String email, String password, String authtoken){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.nickname = nickname;
        this.hobbies = hobbies;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.authtoken = authtoken;
    }    
}
