package com.example.lld.basic.dto;

import lombok.Data;

@Data
public class CreateUserRequest {


    private String username;


    private String password;

    // USER or ADMIN

    private String role;


}

