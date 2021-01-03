package com.HrimDev.project.springboot.config.auth;

import com.HrimDev.project.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@RequiredArgsConstructor
@EnableWebSecurity          //Spring Security 설정을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
           .csrf().disable().headers().frameOptions().disable()//h2-console화면을 사용하기 위해 해당옵션들을 disable함
           .and()
             .authorizeRequests()           //URL별 권한관리를 설정하는 옵션의 시작점(선언 후 antMatchers 옵션 사용)
             .antMatchers("/","css/**","/images/**","/js/**","/h2-console/**").permitAll() //antMatchers:권한관리대상지정->permitAll():전체열람
             .antMatchers("/api/v1/**").hasRole(Role.USER.name())   //USER권한을 가진 사람만 가능
             .anyRequest().authenticated()//설정된 값들 이외의 나머지 URL은 인증된 사용자들에게만 허용(로그인한 사용자들만)
           .and()
             .logout()
                .logoutSuccessUrl("/")  //로그아웃 성공시 '/'주소로 이동
           .and()
             .oauth2Login()     //OAuth2 로그인 기능에 대한 여러설정의 진입점
                .userInfoEndpoint()  //OAuth2로그인 성공 이후 사용자정보를 가져올때 설정
                    .userService(customOAuth2UserService);//소셜로그인 성공시 소셜에서 사용자정보를 가져온 상태에서 추가로 진행하려는 기능을 명시
    }
}
