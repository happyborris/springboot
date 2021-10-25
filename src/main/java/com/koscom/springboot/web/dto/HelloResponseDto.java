package com.koscom.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString // 객체의 내부의 값을 String으로 출력 가능하게 해줌(@Override toString 메소드 과정을 자동화 해주는 어노테이션)
@Getter // getter 생성
@RequiredArgsConstructor // final이 붙은 놈들의 생성자를 자동 생성
public class HelloResponseDto {
    private final String name;
    private final int amount;


}
