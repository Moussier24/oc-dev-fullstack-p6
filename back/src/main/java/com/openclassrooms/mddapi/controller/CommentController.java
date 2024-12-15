package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.CommentResponseDto;
import com.openclassrooms.mddapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentResponseDto>> getArticleComments(@PathVariable Long articleId) {
        return ResponseEntity.ok(commentService.getCommentsByArticle(articleId));
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(commentDto));
    }
}