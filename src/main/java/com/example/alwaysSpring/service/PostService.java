package com.example.alwaysSpring.service;

import com.example.alwaysSpring.domain.posts.Posts;
import com.example.alwaysSpring.domain.posts.PostsRepository;
import com.example.alwaysSpring.dto.posts.PostsCreateRequestDto;
import com.example.alwaysSpring.dto.posts.PostsCreateResponseDto;
import com.example.alwaysSpring.dto.posts.PostsUpdateRequestDto;
import com.example.alwaysSpring.dto.posts.PostsUpdateResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostsRepository postsRepository;

    @Transactional
    public ResponseEntity<PostsCreateResponseDto> create(PostsCreateRequestDto requestDto) {
        postsRepository.save(requestDto.toEntity());
        PostsCreateResponseDto responseDto = new PostsCreateResponseDto(requestDto.toEntity());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<PostsUpdateResponseDto> update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 게시글 입니다. id=" + id)
        );
        posts.update(requestDto.getTitle(), requestDto.getContent());
        PostsUpdateResponseDto responseDto = new PostsUpdateResponseDto(posts);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
