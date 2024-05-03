package com.example.alwaysSpring.service;

import com.example.alwaysSpring.domain.posts.PostsRepository;
import com.example.alwaysSpring.dto.posts.PostsCreateRequestDto;
import com.example.alwaysSpring.dto.posts.PostsCreateResponseDto;
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
}
