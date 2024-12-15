package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.ThemeService;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ThemeController {

    private final ThemeService themeService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Theme>> getAllThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }

    @PostMapping("/{themeId}/subscribe")
    public ResponseEntity<Void> subscribeToTheme(@PathVariable Long themeId) {
        User currentUser = userService.getCurrentUser();
        themeService.subscribeUserToTheme(currentUser, themeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{themeId}/unsubscribe")
    public ResponseEntity<Void> unsubscribeFromTheme(@PathVariable Long themeId) {
        User currentUser = userService.getCurrentUser();
        themeService.unsubscribeUserFromTheme(currentUser, themeId);
        return ResponseEntity.ok().build();
    }
}