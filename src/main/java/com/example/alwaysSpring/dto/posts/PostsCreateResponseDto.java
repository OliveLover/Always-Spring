package com.example.alwaysSpring.dto.posts;

import com.example.alwaysSpring.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsCreateResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsCreateResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
