package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.CommentResponseDto;
import java.util.List;

public interface CommentService {
    List<CommentResponseDto> getCommentsByArticle(Long articleId);

    CommentResponseDto createComment(CommentDto commentDto);
}