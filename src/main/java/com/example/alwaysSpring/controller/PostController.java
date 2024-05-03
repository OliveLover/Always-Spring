package com.example.alwaysSpring.controller;

import com.example.alwaysSpring.dto.posts.*;
import com.example.alwaysSpring.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostsCreateResponseDto> create(@RequestBody PostsCreateRequestDto requestDto) {
        return postService.create(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostsUpdateResponseDto> update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostsResponseDto> findById(@PathVariable Long id) {
        return postService.findById(id);
    }
}
