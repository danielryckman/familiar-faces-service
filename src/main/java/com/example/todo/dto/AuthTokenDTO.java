package com.example.todo.dto;

import lombok.Data;

@Data
public class AuthTokenDTO {
    private String authtoken;
    public AuthTokenDTO(String id){
        authtoken = id;
    }    
}