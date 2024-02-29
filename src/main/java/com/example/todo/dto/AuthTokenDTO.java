package com.example.todo.dto;

import lombok.Data;

@Data
public class AuthTokenDTO {
    private String auth_token;
    public AuthTokenDTO(String id){
        auth_token = id;
    }    
}