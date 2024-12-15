package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.ArticleResponseDto;
import com.openclassrooms.mddapi.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Articles", description = "Endpoints pour la gestion des articles")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "Récupérer le fil d'actualité de l'utilisateur connecté")
    @GetMapping("/feed")
    public ResponseEntity<List<ArticleResponseDto>> getFeed() {
        return ResponseEntity.ok(articleService.getCurrentUserFeed());
    }

    @Operation(summary = "Créer un nouvel article")
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleDto articleDto) {
        return ResponseEntity.ok(articleService.createArticle(articleDto));
    }

    @Operation(summary = "Récupérer un article par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }
}