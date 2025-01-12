package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO pour la mise à jour du profil utilisateur")
public class UserUpdateDto {
    private String email;
    private String username;
    private String password;
}