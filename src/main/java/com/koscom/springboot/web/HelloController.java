package com.koscom.springboot.web;

import com.koscom.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @Controller -> 일반 페이지...? 그런거 보낼 때 사용
@RestController // 일반 API 주고받을 때 사용
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    // getA?name=A&amount=100 에서 A와 100을 RequestParam 어노테이션으로 지정한다
    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam String name,
                                     @RequestParam int amount) {

        HelloResponseDto dto = new HelloResponseDto(name, amount);
        System.out.println("dto:" + dto);
        return dto;
    }
}
