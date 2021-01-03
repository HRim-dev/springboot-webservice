package com.HrimDev.project.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing//JPA Auditing 활성화->변경:config패키지에 JpaConfig생성(Test시 오류방지)
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        //내장 WAS(웹 어플리케이션 서버 실행):언제 어디서나 같은 환경에서 스프링 부트를 배포가능
        SpringApplication.run(Application.class,args);

    }
}
