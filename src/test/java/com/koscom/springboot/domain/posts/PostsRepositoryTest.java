package com.koscom.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
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

    // LocalDate : 일자
    // LocalDateTime : 일시
    @Test
    public void 등록시간_수정시간이_저장된다() {
        // given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // 지금 일시와 수정 일시는 2019년보다는 뒤라는 생각
        // 매일 수행할 때마다 일시가 바뀌니까 특정 일시를 확정할 수 없는 테스트

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        assertThat(posts.getCreatedTime()).isAfter(now); // 2019년보다 생성일이 뒤에 있느냐
        assertThat(posts.getUpdatedTime()).isAfter(now); // 2019년보다 수정일이 뒤에 있느냐
    }
}
