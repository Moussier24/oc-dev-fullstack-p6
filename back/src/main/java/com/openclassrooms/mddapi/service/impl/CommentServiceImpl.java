package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.CommentResponseDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Override
    public List<CommentResponseDto> getCommentsByArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        return commentRepository.findByArticleOrderByCreatedAtDesc(article)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto createComment(CommentDto commentDto) {
        User currentUser = userService.getCurrentUser();
        Article article = articleRepository.findById(commentDto.getArticleId())
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setAuthor(currentUser);
        comment.setArticle(article);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return convertToDto(savedComment);
    }

    private CommentResponseDto convertToDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setAuthorUsername(comment.getAuthor().getUsername());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}