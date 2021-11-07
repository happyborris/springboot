package com.koscom.springboot.config.auth.login;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 어노테이션이 선언될 수 있는 위치를 나타냄. ex. PARAMETER => 매개변수 위치에 선언 가능
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
