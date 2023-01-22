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
    private String dob;
    private String gender;
    private String hobbies;
    private String songs;
    private Set<PhotoDTO> photo;
    private UserDTO user;
}
