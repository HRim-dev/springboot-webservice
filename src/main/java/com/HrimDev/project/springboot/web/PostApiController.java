package com.HrimDev.project.springboot.web;

import com.HrimDev.project.springboot.service.PostsService;
import com.HrimDev.project.springboot.web.dto.PostsListResponseDto;
import com.HrimDev.project.springboot.web.dto.PostsResponseDto;
import com.HrimDev.project.springboot.web.dto.PostsSaveRequestDto;
import com.HrimDev.project.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostsService postsService;

    //게시긓 등록
    @PostMapping("/api/v1/posts")
    public long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    //게시글 수정
    @PutMapping("/api/v1/posts/{id}")
    public long update(@PathVariable long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable long id){
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAll() {
        return postsService.findAllDesc();
    }

    //게시글 삭제
    @DeleteMapping("/api/v1/posts/{id}")
    public long delete(@PathVariable long id){
        postsService.delete(id);
        return id;
    }
}
