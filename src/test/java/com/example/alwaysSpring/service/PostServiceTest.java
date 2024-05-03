package com.example.alwaysSpring.service;

import com.example.alwaysSpring.domain.posts.Posts;
import com.example.alwaysSpring.domain.posts.PostsRepository;
import com.example.alwaysSpring.dto.posts.PostsCreateRequestDto;
import com.example.alwaysSpring.dto.posts.PostsCreateResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostServiceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    void create_post_and_verify_saved_data() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String author = "author";

        PostsCreateRequestDto requestDto = PostsCreateRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<PostsCreateResponseDto> responseEntity = restTemplate
                .postForEntity(url, requestDto, PostsCreateResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody())
                .extracting("title", "content", "author")
                .containsExactly(title, content, author);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.getFirst())
                .extracting(title, content, author)
                .containsExactly(title, content, author);
    }
}