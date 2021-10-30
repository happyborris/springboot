package com.koscom.springboot.web;

import com.koscom.springboot.domain.posts.Posts;
import com.koscom.springboot.domain.posts.PostsRepository;
import com.koscom.springboot.web.dto.posts.PostsSaveRequestDto;
import com.koscom.springboot.web.dto.posts.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 실제로 임시 톰캣을 띄워서 테스트 수행함, PORT 번호는 충돌을 피하기 위해 랜덤값으로(Postman 날리듯이!)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate; // 실제 임시 톰캣 띄워서 진짜 rest를 날려볼거임! 따라서 테스트용 Rest Template 사용

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    void API를_통해서_Post가_등록_된다() {
        // given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts" ;

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK); // Http 응답 코드가 200
        assertThat(responseEntity.getBody()).isGreaterThan(0L); // auto increment 되면 1 이상

        List<Posts> result = postsRepository.findAll();
        assertThat(result.get(0).getTitle()).isEqualTo(title);
        assertThat(result.get(0).getContent()).isEqualTo(content);
    }

    @Test
    void API를_통해서_Post가_수정_된다() {
        // given
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

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId ;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK); // Http 응답 코드가 200
        assertThat(responseEntity.getBody()).isGreaterThan(0L); // auto increment 되면 1 이상

        List<Posts> result = postsRepository.findAll();
        assertThat(result.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(result.get(0).getContent()).isEqualTo(expectedContent);
    }
}
