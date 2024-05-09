package com.example.alwaysSpring.dto.posts;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostsUpdateRequestDto {
    private String title;
    private String content;
}
