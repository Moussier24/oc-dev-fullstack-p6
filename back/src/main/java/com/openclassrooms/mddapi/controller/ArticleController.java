package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.ArticleResponseDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/feed")
    public ResponseEntity<List<ArticleResponseDto>> getFeed() {
        return ResponseEntity.ok(articleService.getCurrentUserFeed());
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleDto articleDto) {
        return ResponseEntity.ok(articleService.createArticle(articleDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }
}