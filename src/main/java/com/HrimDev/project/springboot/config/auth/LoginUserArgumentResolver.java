package com.HrimDev.project.springboot.config.auth;

import com.HrimDev.project.springboot.config.auth.dto.SessionUser;
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

    @Override
    public boolean supportsParameter(MethodParameter parameter) {//컨트롤러 메소드의 특정 파라미터를 지원하는지 판단
        boolean isLoginUserAnnotation=parameter.getParameterAnnotation(LoginUser.class)!=null;//LoginUser어노테이션이 붙어있고
        boolean isUserClass= SessionUser.class.equals(parameter.getParameterType());          //파라미터 클래스타입이 SessionUser인 경우 true 반환

        return isLoginUserAnnotation&&isUserClass;
    }


    @Override  //파라미터에 전달할 객체 생성(세션에서 객체를 가져옴)
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
