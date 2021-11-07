package com.koscom.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// JPA에 관련된 설정들이 모여있는 곳
// Spring Boot로 오면서 대부분 관례 중심 개발을 하게 됨 -> 즉, 전 세계 개발자들이 보통 많이 쓰는 기능, 템플릿을 어노테이션으로 불러오기만 하는 형태
@Configuration // Spring에서 설정 클래스임을 알려주는 어노테이션
@EnableJpaAuditing // JPA Auditing 활성화
public class JpaConfig {
}
