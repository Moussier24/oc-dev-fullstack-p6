package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    private String emailOrUsername;
    private String password;
}