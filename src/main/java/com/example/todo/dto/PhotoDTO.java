package com.example.todo.dto;

import lombok.Data;
import com.example.todo.dto.TestDTO;
import com.example.todo.dto.RecordDTO;
import com.example.todo.dto.FamilymemberDTO;
import com.example.todo.dto.UserDTO;

@Data
public class PhotoDTO {

    private String id;
    private long datetoshow;
    private long datelastviewed;
    private long datecreated;
    private String name;
    private String description;
    private String type;
    private String image;
    private String comment;
    private UserDTO user;
    private FamilymemberDTO familymember;
    private TaskDTO task;
    private RecordDTO record;
}
