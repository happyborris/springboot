package com.koscom.springboot.config.auth.login;

import com.koscom.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    // 기준을 제시함
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 어노테이션이 LoginUser 인지 체크
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        // 파라미터가 SessionUser Type 인지 체크
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    // 액션을 제시함
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }

    // 즉, 기준에 맞으면 액션을 취해줌
    // ex)
    // public void test(@LoginUser SessionUser user) { } => LoginUser 어노테이션을 사용하면서 SessionUser 타입의 파라미터이므로 액션이 실행됨
}
