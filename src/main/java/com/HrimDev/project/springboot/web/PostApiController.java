package com.HrimDev.project.springboot.web;

import com.HrimDev.project.springboot.domain.posts.PostsRepository;
import com.HrimDev.project.springboot.service.posts.PostsService;
import com.HrimDev.project.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

}
