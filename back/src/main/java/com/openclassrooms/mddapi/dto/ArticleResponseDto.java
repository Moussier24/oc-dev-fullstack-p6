package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Réponse d'un article")
public class ArticleResponseDto {
    private Long id;

    private String title;

    private String content;

    @Schema(name = "author_username", example = "john_doe")
    @JsonProperty("author_username")
    private String authorUsername;

    @Schema(name = "theme_name", example = "Frontend")
    @JsonProperty("theme_name")
    private String themeName;

    @Schema(name = "created_at", example = "2024-01-15T14:30:00Z")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}