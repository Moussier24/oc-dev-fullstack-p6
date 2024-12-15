package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.ThemeService;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public List<ThemeDto> getAllThemes() {
        return themeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ThemeDto convertToDto(Theme theme) {
        User currentUser = userService.getCurrentUser();
        ThemeDto dto = new ThemeDto();
        dto.setId(theme.getId());
        dto.setName(theme.getName());
        dto.setDescription(theme.getDescription());
        dto.setSubscribersCount(theme.getSubscribers() != null ? theme.getSubscribers().size() : 0);
        dto.setArticlesCount(theme.getArticles() != null ? theme.getArticles().size() : 0);
        dto.setSubscribed(theme.getSubscribers().contains(currentUser));
        return dto;
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

    @Override
    public List<ThemeDto> getSubscribedThemes() {
        User currentUser = userService.getCurrentUser();
        return currentUser.getSubscribedThemes().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}