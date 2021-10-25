package com.koscom.springboot.web.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// 여긴 롬복만 테스트할거니까 스프링 어노테이션이 필요 없음
public class HelloResponseDtoTest {

    @Test
    void 롬복의_getter가_생성된다() {
        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
