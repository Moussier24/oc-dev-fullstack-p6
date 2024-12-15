package com.openclassrooms.mddapi.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private String themeName;
    private LocalDateTime createdAt;
}