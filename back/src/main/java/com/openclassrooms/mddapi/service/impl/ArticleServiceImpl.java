package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.ArticleResponseDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ThemeRepository themeRepository;
    private final UserService userService;

    @Override
    public List<ArticleResponseDto> getCurrentUserFeed() {
        User currentUser = userService.getCurrentUser();
        List<Theme> subscribedThemes = new ArrayList<>(currentUser.getSubscribedThemes());
        return articleRepository.findByThemeInOrderByCreatedAtDesc(subscribedThemes)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ArticleResponseDto createArticle(ArticleDto articleDto) {
        User currentUser = userService.getCurrentUser();
        Theme theme = themeRepository.findById(articleDto.getThemeId())
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found"));

        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setAuthor(currentUser);
        article.setTheme(theme);
        article.setCreatedAt(LocalDateTime.now());

        Article savedArticle = articleRepository.save(article);
        return convertToDto(savedArticle);
    }

    @Override
    public ArticleResponseDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        return convertToDto(article);
    }

    private ArticleResponseDto convertToDto(Article article) {
        ArticleResponseDto dto = new ArticleResponseDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setAuthorUsername(article.getAuthor().getUsername());
        dto.setThemeName(article.getTheme().getName());
        dto.setCreatedAt(article.getCreatedAt());
        return dto;
    }
}