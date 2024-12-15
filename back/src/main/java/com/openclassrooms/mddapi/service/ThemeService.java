package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import java.util.List;

public interface ThemeService {
    List<ThemeDto> getAllThemes();

    List<ThemeDto> getSubscribedThemes();

    Theme getThemeById(Long id);

    void subscribeUserToTheme(User user, Long themeId);

    void unsubscribeUserFromTheme(User user, Long themeId);
}