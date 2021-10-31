package com.koscom.springboot.web.dto.posts;

import com.koscom.springboot.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 제한해야 하는데, 테스트 코드 짤 때 ResponseEntity 만들 때 디폴트 생성자 필요해서 넣음
public class PostsResponseDto {
    private Long id; // pk
    private String title;
    private String content;
    private String author;

    // 이렇게 하면 Entity로만 Dto를 생성할 수 있게 제한함!
    public PostsResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.author = posts.getAuthor();
    }
}
