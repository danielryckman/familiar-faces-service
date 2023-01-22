package com.example.todo.dto;

import lombok.Data;
import com.example.todo.dto.UserDTO;

@Data
public class TaskDTO {

    private long id;
    private String name;
    private String description;
    private String createdDate;
    private String url;
    private long schedule;
    private int repeat;
    private long repeatstart;
    private long repeatend;
    private String image;
    private UserDTO user;
}
