package com.HrimDev.project.springboot.web;

import com.HrimDev.project.springboot.config.auth.dto.SessionUser;
import com.HrimDev.project.springboot.service.PostsService;
import com.HrimDev.project.springboot.web.dto.PostsResponseDto;
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
    //로그인
    private final HttpSession httpSession;

    //게시글 전체 조회
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllDesc());

        //로그인
        SessionUser user=(SessionUser) httpSession.getAttribute("user");

        if(user!=null){
            model.addAttribute("userName",user.getName());
        }
        return "index"; // index.mustache로 전환(View Resolver(:URL 요청결과를 전달할 타입과 값을 지정하는 관리자)가 처리)
    }

    //게시글 등록
    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    //게시글 수정
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable long id, Model model){
        PostsResponseDto dto=postsService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }
}
