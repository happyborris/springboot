package com.koscom.springboot.service;

import com.koscom.springboot.domain.posts.Posts;
import com.koscom.springboot.domain.posts.PostsRepository;
import com.koscom.springboot.web.dto.posts.PostsListResponseDto;
import com.koscom.springboot.web.dto.posts.PostsResponseDto;
import com.koscom.springboot.web.dto.posts.PostsSaveRequestDto;
import com.koscom.springboot.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// 요즘은 서비스 단에서 Impl 방식을 잘 안씀 -> 프로젝트 경험이 쌓여보니 서비스가 바뀔 일이 많지 않아서
@RequiredArgsConstructor // final로 선언된 필드들은 생성자 항목에 자동 포함시켜서 생성자 생성
@Service // Spring bean 등록 & Service 클래스 선언
public class PostsService {
    private final PostsRepository postsRepository;

    // @Transactional 을 걸면 rollback 기능도 있지만, row lock 기능도 함!
    // 등록
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // 수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto dto) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자는 없습니다. id = " + id));

        // 왜 객체만 update하고 repository = DB에는 반영을 안해주지?
        // Dirty Checking - 더티 체킹
        // @Transactional 어노테이션이 존재할 때, JPA를 사용하면
        // repository에서 가져온 entity를 변경하면 Transaction이 끝날 때 변경사항을 DB에 자동 반영
        entity.update(dto.getTitle(), dto.getContent());

        return entity.getId();
    }

    // 조회
    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // 조회용 DB로만 감, readOnly 없으면 마스터 DB로 감
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }
}
