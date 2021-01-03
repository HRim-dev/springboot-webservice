package com.HrimDev.project.springboot.web;

import com.HrimDev.project.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)//스프링부트 테스트와 JUnit 사이의 연결자
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = {
                @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,
                                        classes = SecurityConfig.class)
            })//JUnitTest 오류 해결(https://jjunii486.tistory.com/171)
public class HelloControllerTest {
    @Autowired//스프링이 관리하는 빈을 주입받음
    private MockMvc mvc;//웹 API를 테스트할 때 사용(HTTP GET,POST 등에 대한 API 테스트 가능)

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴() throws Exception {
        String hello="hello";

        //get import 오류해결:https://junho85.pe.kr/1598
        mvc.perform(get("/hello"))//MockMvc를 통해 /hello 주소로 get요청
                .andExpect(status().isOk())     //mvc.perform의 결과 검증(HTTP header Staus:200,404,500)=>isOK는 200인지 아닌지 검증
                .andExpect(content().string(hello));//응답 본문내용 검증(리턴값이 hello가 맞는지 검증
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴() throws Exception{
        String name="hello";
        int amount=1000;

        mvc.perform(//perform 메서드는 예외처리 필요
                get("/hello/dto").param("name",name)
                                            .param("amount",String.valueOf(amount))//param: API테스트할 떄 사용될 요청 파라미터 설정
                                                                                        // (String만 허용, 숫자/날짜 데이터의 경우는 문자열로 변경필요)
        ).andExpect(status().isOk())
         .andExpect(jsonPath("$.name",is(name)))    //JSON 응답값을 필드별로 검증($를 기준으로 필드명 명시)
         .andExpect(jsonPath("$.amount",is(amount)));
    }
}