package com.koscom.springboot.web;

import com.koscom.springboot.config.auth.dto.SessionUser;
import com.koscom.springboot.config.auth.login.LoginUser;
import com.koscom.springboot.service.PostsService;
import com.koscom.springboot.web.dto.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    // 세션 : 서버 영역에 저장
    // 쿠키 : 클라이언트 영역에 저장
    // 쿠키가 저장되지 않는 특별한 브라우저에서 접속하면 세션은 사용할 수 있을까? => 쿠키 & 세션 로그인 방식에서는 No (단, JWT는 다른 얘기)
    // 사용자가 Http Request => Body & Param & Header
    // 그 중 request header에 세션 key가 존재함!
    // 쿠키로 넘어온 세션 Key (JSESSIONID, 7813abcde)
    // HttpSession에서는 ("user", JSESSIONID) / (7813abcde, 주형일의 세션정보)

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) { // Model은 전달할 데이터를 담는 그릇
        model.addAttribute("posts", postsService.findAllDesc()); // id에 내림차순으로, List 전달, key 값은 "posts" value 값은 postsService.findAllDesc()

        if(user != null) {
            model.addAttribute("user", user.getName()); // 세션에 저장된 값을 모델에 넣어줌.
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}