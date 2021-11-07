package com.koscom.springboot.config.auth.dto;

import com.koscom.springboot.domain.user.Role;
import com.koscom.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    // 팩토리 메소드 패턴, 2번째 인자는 구글도 같이 만들 경우에 필요한 친구. 생성자를 쓰지 않고 버전이 다른 객체를 생성하는 느낌? ex. ofNaver() -> 네이버 버전, ofGoogle() -> 구글 버전
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        } // else if("facebook") { return ofFacebook(); } ~~ 이런 식으로 감

        // return ofGoogle(userNameAttributeName, attributes);
        throw new IllegalArgumentException("네이버 외에는 로그인 할 수 없습니다");
    }

    // 이 부분은 구글
//    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .picture((String) attributes.get("picture"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response"); // JSON의 response 영역을 가져옴.

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image")) // 여기까지 3개의 속성을 가지고 OAuthAttributes 객체를 생성함!
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // OAuth 과정 중에, 새로운 사용자라면 바로 User Entity를 만들어서 가입시키기 위함.
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER) // api 호출은 USER 권한이 있어야 하기 때문에
                .build();
    }
}
