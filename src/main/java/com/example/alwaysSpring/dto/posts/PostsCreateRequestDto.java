package com.example.alwaysSpring.dto.posts;

import com.example.alwaysSpring.domain.posts.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostsCreateRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
