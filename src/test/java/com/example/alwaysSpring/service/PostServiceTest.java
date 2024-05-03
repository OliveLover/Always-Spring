package com.example.alwaysSpring.service;

import com.example.alwaysSpring.domain.posts.Posts;
import com.example.alwaysSpring.domain.posts.PostsRepository;
import com.example.alwaysSpring.dto.posts.PostsCreateRequestDto;
import com.example.alwaysSpring.dto.posts.PostsCreateResponseDto;
import com.example.alwaysSpring.dto.posts.PostsUpdateRequestDto;
import com.example.alwaysSpring.dto.posts.PostsUpdateResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
    void createPost_and_verifySavedDdata() throws Exception {
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
        assertThat(all.get(0))
                .extracting("title", "content", "author")
                .containsExactly(title, content, author);
    }

    @Test
    void givenValidPostId_whenUpdatePost_thenPostIsUpdatedCorrectly() throws Exception {
        //given
        Posts savePosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savePosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<PostsUpdateResponseDto> responseEntity = restTemplate
                .exchange(url, HttpMethod.PUT, requestEntity, PostsUpdateResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .extracting("id", "title", "content", "author")
                .containsExactly(updateId, expectedTitle, expectedContent, "author");

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0))
                .extracting("id", "title", "content", "author")
                .containsExactly(updateId, expectedTitle, expectedContent, "author");
    }

    @Test
    void findById_and_verify() throws Exception {
        //given
        Posts savePosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long findId = savePosts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + findId;

        //when
        ResponseEntity<PostsUpdateResponseDto> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, null, PostsUpdateResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .extracting("id", "title", "content", "author")
                .containsExactly(findId, "title", "content", "author");
    }
}