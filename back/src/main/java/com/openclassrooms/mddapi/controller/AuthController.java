package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.AuthResponseDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegistrationDto;
import com.openclassrooms.mddapi.dto.UserUpdateDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Authentication", description = "Endpoints pour l'authentification")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Operation(summary = "Inscription d'un nouvel utilisateur")
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody UserRegistrationDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(summary = "Connexion d'un utilisateur")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLoginDto request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @Operation(summary = "Mettre à jour le profil de l'utilisateur connecté")
    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(@RequestBody UserUpdateDto updateDto) {
        User currentUser = userService.getCurrentUser();
        User updatedUser = userService.updateUser(currentUser.getId(), updateDto);

        UserDto userDto = new UserDto();
        userDto.setId(updatedUser.getId());
        userDto.setEmail(updatedUser.getEmail());
        userDto.setUsername(updatedUser.getUsername());

        return ResponseEntity.ok(userDto);
    }
}