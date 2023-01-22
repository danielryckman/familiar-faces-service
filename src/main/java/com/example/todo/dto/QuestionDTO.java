package com.example.todo.dto;

import lombok.Data;
import com.example.todo.dto.TestDTO;

@Data
public class QuestionDTO {

    private long qid;
    private String name;
    private String description;
    private String hint;
    private String solution;
    private String answer;
    private String category;
    private String timetoanswer;
    private String score;
    private String image;
    private TestDTO test;
}
