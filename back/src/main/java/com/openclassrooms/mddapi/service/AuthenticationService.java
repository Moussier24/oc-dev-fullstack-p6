package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegistrationDto;

public interface AuthenticationService {
    String register(UserRegistrationDto request);

    String login(UserLoginDto request);
}