package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.ArticleResponseDto;
import java.util.List;

public interface ArticleService {
    List<ArticleResponseDto> getCurrentUserFeed();

    ArticleResponseDto createArticle(ArticleDto articleDto);

    ArticleResponseDto getArticleById(Long id);
}