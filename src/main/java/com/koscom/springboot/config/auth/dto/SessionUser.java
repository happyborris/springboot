package com.koscom.springboot.config.auth.dto;

import com.koscom.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

// 사실, User 클래스랑 크게 다른건 없지만, 나누는 이유 => implements Serializable 코드로 직렬화를 할 때, 세션에 User 한 명의 정보를 전부 담아두기엔 부하가 너무 큼! => 필요한 만큼만 세션에 유지시키기 위해 분리!
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}