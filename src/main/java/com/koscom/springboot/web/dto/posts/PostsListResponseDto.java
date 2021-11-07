package com.koscom.springboot.web.dto.posts;

import com.koscom.springboot.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
// @NoArgsConstructor -> 테스트 코드를 짤 때 필요
public class PostsListResponseDto {
    private final Long id;
    private final String title;
    private final String author;
    private final LocalDateTime updatedDate;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.updatedDate = entity.getUpdatedTime();
    }
}