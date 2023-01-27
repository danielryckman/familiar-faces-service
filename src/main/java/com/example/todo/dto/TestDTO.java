package com.example.todo.dto;

import lombok.Data;
import com.example.todo.dto.QuestionDTO;
import com.example.todo.dto.UserDTO;
import java.util.Set;
import java.util.List;
@Data
public class TestDTO {

    private long id;
    private String name;
    private Long endtime;
    private Long starttime;
    private Set<QuestionDTO> questions;
    private int score;
    private String subscores;
    private UserDTO user;
    
    public void setId(Long id){
    	this.id =id;
    }
}
