package com.koscom.springboot.config;

import com.koscom.springboot.config.auth.login.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// Controller에 관련된 설정들이 모여있는 곳
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    // 전체에서 LoginUserArgumentResolver을 사용 가능하게 해줌 -> LoginUser 어노테이션을 사용 가능하게 해줌
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
