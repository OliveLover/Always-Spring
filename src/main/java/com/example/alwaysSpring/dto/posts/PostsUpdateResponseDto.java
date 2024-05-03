package com.example.alwaysSpring.dto.posts;

import com.example.alwaysSpring.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsUpdateResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
