package com.koscom.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Test
    void 게시글저장_불러오기() {
        String title = "테스트 제목";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                        .title(title)
                        .content(content)
                        .build());

        List<Posts> result = postsRepository.findAll(); // 모든 row를 반환함.

        System.out.println("id : " + result.get(0).getId()); // db가 만들어준 값
        System.out.println("본문 : " + result.get(0).getTitle()); // 내가 넣은 값

        assertThat(result.get(0).getTitle()).isEqualTo(title);
        assertThat(result.get(0).getContent()).isEqualTo(content);
    }

    @AfterEach // 테스트 하나 끝날 때마다 해주는 작업
    void tearDown() {
        postsRepository.deleteAll(); // 디비를 비워줘야 함, 이전에 한 테스트에 의해 영향을 받을 수 있기 때문에
    }

    @Test
    void 게시글저장_불러오기2() {
        String title = "테스트 제목";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());

        List<Posts> result = postsRepository.findAll();

        System.out.println("size : " + result.size());

        assertThat(result).hasSize(1);
    }

    @Test
    void 게시글저장_불러오기3() {
        String title = "테스트 제목";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());

        List<Posts> result = postsRepository.findAll();

        System.out.println("size : " + result.size());

        assertThat(result).hasSize(1);
    }
}
