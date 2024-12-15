package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegistrationDto;
import com.openclassrooms.mddapi.model.User;

public interface UserService {
    User register(UserRegistrationDto registrationDto);

    User login(UserLoginDto loginDto);

    User getCurrentUser();

    User updateUser(Long id, UserRegistrationDto userDto);

    void validatePassword(String password);
}