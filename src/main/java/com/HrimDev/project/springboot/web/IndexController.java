package com.HrimDev.project.springboot.web;

import com.HrimDev.project.springboot.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllDesc());
        return "index"; // index.mustache로 전환(View Resolver(:URL 요청결과를 전달할 타입과 값을 지정하는 관리자)가 처리)
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
