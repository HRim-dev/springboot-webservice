package com.HrimDev.project.springboot.service.posts;

import com.HrimDev.project.springboot.domain.posts.PostsRepository;
import com.HrimDev.project.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor//final로 선언된 모든 필드를 인자값으로하는 생성자를 생성->롬복어노테이션을 사용하는 이유: 해당 클래스의 의존성 관계가 변경될때 마다 생성자코드를 변경하는 번거로움 해결
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
