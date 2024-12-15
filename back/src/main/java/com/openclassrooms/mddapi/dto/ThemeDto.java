package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Thème avec compteurs")
public class ThemeDto {
    private Long id;
    private String name;
    private String description;

    @Schema(name = "subscribers_count", example = "42")
    @JsonProperty("subscribers_count")
    private int subscribersCount;

    @Schema(name = "articles_count", example = "15")
    @JsonProperty("articles_count")
    private int articlesCount;

    @Schema(name = "is_subscribed", example = "true", description = "Indique si l'utilisateur courant est abonné à ce thème")
    @JsonProperty("is_subscribed")
    private boolean isSubscribed;
}