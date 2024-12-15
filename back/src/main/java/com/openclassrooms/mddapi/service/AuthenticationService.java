package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.AuthResponseDto;
import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegistrationDto;

public interface AuthenticationService {
    AuthResponseDto register(UserRegistrationDto request);

    AuthResponseDto login(UserLoginDto request);
}