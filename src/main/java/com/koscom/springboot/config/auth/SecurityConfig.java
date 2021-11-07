package com.koscom.springboot.config.auth;

import com.koscom.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // security 활성화 시킨다는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Value("${security.enabled:true}") // security를 enable 하겠다
    private boolean securityEnabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 자잘한 보안 기능은 꺼두고
                .headers().frameOptions().disable() // 얘도 꺼두고
                .and()
                    .authorizeRequests()
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() // static 불러오는 애들은 모든 사용자에게 허용
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // /api/v1/~ url에 접근하는 것들은 Role.USER 권한을 가진 사용자만 허용
                        .anyRequest().authenticated() // 나머지는 인증만 되면 사용 가능, 윗줄과 다른 점은, 윗줄은 'USER' Role을 가진 사용자에게만 권한을 주는 것. 즉, 인가
                .and()
                    .logout() // 로그아웃 되면
                        .logoutSuccessUrl("/") // 최초 페이지로 redirect
                .and()
                    .oauth2Login()// 로그인이 되면
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService); // 우리가 직접 만든 커스텀 서비스를 실행하게 해줘~
    }

    // 테스트 할 경우 security 무시
    @Override
    public void configure(WebSecurity web) throws Exception {
        if(!securityEnabled) {
            web.ignoring().antMatchers("/**");
        }
    }
}
