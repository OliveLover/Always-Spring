package com.example.alwaysSpring.domain.posts;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String author;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
