package com.koscom.springboot.config.auth;

import com.koscom.springboot.config.auth.dto.OAuthAttributes;
import com.koscom.springboot.config.auth.dto.SessionUser;
import com.koscom.springboot.domain.user.User;
import com.koscom.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 어느 SNS에서 인증하는가 -> naver (properties에 {registrationId} 영역)
                                                                                        // OAuth가 여러 개일 경우에, 정보를 빼오는 방식이 각각 달라서, 어디서 빼오는지 확인하기 위해 변수로 저장
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // properties의 {user-name-attribute}영역과 맵핑 -> response 영역을 가져옴

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); // OAuth 끝나고 난 뒤 반환되는 정보를 여기로 가져올거임.

        User user = saveOrUpdate(attributes); // 저장하거나 업데이트 할 것임
        httpSession.setAttribute("user", new SessionUser(user)); // 로그인 한 사용자 정보를 세션에 담기 위한 부분. User 정보를 전부 담으면 부하가 크니까, SessionUser 객체를 따로 만듦.

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    // 가입을 안한 사용자라면 => 가입을 시키고
    // 이미 가입된 사용자이면 => 혹시나 변경된 정보를 DB에 반영한다.
    // 세션에서 인증 정보만 저장하면 => 로그인 상태
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail()) // 이메일 정보로 유저를 찾았는데,
                .map(savedUser -> savedUser.update(attributes.getName(), attributes.getPicture())) // 있으면 업데이트,
                .orElse(attributes.toEntity()); // 없으면 가입

        return userRepository.save(user); // @Transactional이 있으면 entity가 바뀌고 transaction이 끝나는 순간 업데이트가 되지만, 여긴 없으므로 save 해줘야함. 근데 JPA의 save => insert or update!
    }
}
