package com.koscom.springboot.web;

import com.koscom.springboot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class) // JUnit5한테 스프링으로 테스트하겠다는 것을 알림
@WebMvcTest(controllers = HelloController.class, // web에 관련된 컨트롤러 가지고만 테스트를 짤 때 사용, HelloController만 읽어라~
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class) // SecurityConfig 클래스를 자동으로 읽는 것을, 읽지 말라고 지시
        })
//@SpringBootTest -> 얘를 써도 되는데 그러면 필요없는 많은 다른 요소들까지 끌어오므로 불필요함.
public class HelloControllerTest {

    @Autowired // 자동으로 스프링 빈 주입
    MockMvc mvc; // 가짜 톰캣을 자동으로 올려줌

    @WithMockUser(roles = "USER") // 이걸 안하면 302가 뜸 (302 => 임시 redirect, 로그인이 안 됐으니 네이버 로그인 페이지로 redirect 시키는 것!). 임시로 user 지정해줌으로써 에러 회피
                                    // 단점 : MockMvc 가 있을 때만 사용 가능
    @Test
    void hello주소로요청이오면_hello가_리턴된다() throws Exception {
        String expectResult = "hello";

        mvc.perform(get("/hello")) // hello주소로 get 요청이 오면
                .andExpect(status().isOk()) // http status는 정상(200)이여야 하고
                .andExpect(content().string(expectResult)); // 그 안의 내용물(ResponseBody)은 string값의 expectResult여야한다.
    }

    @WithMockUser(roles = "USER")
    @Test
    void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount))) // 여기까지가 request test
                .andExpect(status().isOk()) // 여기서부터 검증
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

    @WithMockUser(roles = "USER")
    @Test
    void amount가없으면_응답코드가400이_된다() throws Exception {
        String name = "hello";

        mvc.perform(get("/hello/dto")
                        .param("name", name))
                .andExpect(status().isBadRequest());
    }
}
