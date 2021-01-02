package com.HrimDev.project.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index"; // index.mustache로 전환(View Resolver(:URL 요청결과를 전달할 타입과 값을 지정하는 관리자)가 처리)
    }
}
