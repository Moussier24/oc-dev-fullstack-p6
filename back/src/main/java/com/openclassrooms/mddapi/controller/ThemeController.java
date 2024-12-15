package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.ThemeService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Themes", description = "Endpoints pour la gestion des thèmes")
@SecurityRequirement(name = "bearerAuth")
public class ThemeController {

    private final ThemeService themeService;
    private final UserService userService;

    @Operation(summary = "Récupérer tous les thèmes disponibles")
    @GetMapping
    public ResponseEntity<List<ThemeDto>> getAllThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }

    @Operation(summary = "S'abonner à un thème")
    @PostMapping("/{themeId}/subscribe")
    public ResponseEntity<Void> subscribeToTheme(@PathVariable Long themeId) {
        User currentUser = userService.getCurrentUser();
        themeService.subscribeUserToTheme(currentUser, themeId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Se désabonner d'un thème")
    @PostMapping("/{themeId}/unsubscribe")
    public ResponseEntity<Void> unsubscribeFromTheme(@PathVariable Long themeId) {
        User currentUser = userService.getCurrentUser();
        themeService.unsubscribeUserFromTheme(currentUser, themeId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Récupérer les thèmes auxquels l'utilisateur est abonné")
    @GetMapping("/subscribed")
    public ResponseEntity<List<ThemeDto>> getSubscribedThemes() {
        return ResponseEntity.ok(themeService.getSubscribedThemes());
    }
}