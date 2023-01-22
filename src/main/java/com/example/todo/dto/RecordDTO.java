package com.example.todo.dto;

import lombok.Data;
import com.example.todo.dto.TestDTO;
import com.example.todo.dto.RecordDTO;
import com.example.todo.dto.FamilymemberDTO;
import com.example.todo.dto.PhotoDTO;
import com.example.todo.dto.UserDTO;
import java.util.Set;

@Data
public class RecordDTO {

    private long id;
    private String date;
    private long apptime;
    private long phototime;
    private long testtime;
    private UserDTO user;
    private Set<PhotoDTO> photo;
    private Set<TestDTO> tests;
}
