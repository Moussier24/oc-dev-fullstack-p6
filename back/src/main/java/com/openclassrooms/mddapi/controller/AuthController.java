package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegistrationDto;
import com.openclassrooms.mddapi.service.AuthenticationService;
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

    @Operation(summary = "Inscription d'un nouvel utilisateur")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(summary = "Connexion d'un utilisateur")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}