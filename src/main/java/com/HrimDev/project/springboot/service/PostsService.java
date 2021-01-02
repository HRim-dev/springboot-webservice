package com.HrimDev.project.springboot.service;

import com.HrimDev.project.springboot.domain.posts.Posts;
import com.HrimDev.project.springboot.domain.posts.PostsRepository;
import com.HrimDev.project.springboot.web.dto.PostsListResponseDto;
import com.HrimDev.project.springboot.web.dto.PostsResponseDto;
import com.HrimDev.project.springboot.web.dto.PostsSaveRequestDto;
import com.HrimDev.project.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor//final로 선언된 모든 필드를 인자값으로하는 생성자를 생성->롬복어노테이션을 사용하는 이유: 해당 클래스의 의존성 관계가 변경될때 마다 생성자코드를 변경하는 번거로움 해결
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    //게시글 등록
    @Transactional
    public long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //게시글 수정
    @Transactional
    public long update(long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(long id){
        Posts entity=postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly =true)//readOnly=true: 트랜잭션 범위는 유지, 조회기능만 남음(조회 속도 개선)->등록, 수정, 삭제 기능이 없는 서비스메소드에 사용을 추천
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                              .map(PostsListResponseDto::new)//=.map(posts->new PostsListResponseDto(posts)
                              .collect(Collectors.toList());

    }

    //게시글 삭제
    @Transactional
    public void delete(long id){
        Posts posts=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        postsRepository.delete(posts);

    }
}
