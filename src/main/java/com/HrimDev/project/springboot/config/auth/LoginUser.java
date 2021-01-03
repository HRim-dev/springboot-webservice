package com.HrimDev.project.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//문제: 세션 값이 필요할 때마다 직접 세션에서 가져와야 함->같은 코드 반복은 불필요
//개선: 메소드 인자로 세션값을 바로 받을 수 있도록 어노테이션 생성
@Target(ElementType.PARAMETER)//이 어노테이션이 생성될 수 있는 위치 지정, PARAMETER: 메소드의 파라미터로 선언된 객체에서만 사용가능
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {//어노테이션 클래스

}
