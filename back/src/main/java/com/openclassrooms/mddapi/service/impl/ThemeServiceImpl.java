package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;

    @Override
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    @Override
    public Theme getThemeById(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found"));
    }

    @Override
    public void subscribeUserToTheme(User user, Long themeId) {
        Theme theme = getThemeById(themeId);
        user.getSubscribedThemes().add(theme);
        userRepository.save(user);
    }

    @Override
    public void unsubscribeUserFromTheme(User user, Long themeId) {
        Theme theme = getThemeById(themeId);
        user.getSubscribedThemes().remove(theme);
        userRepository.save(user);
    }
}