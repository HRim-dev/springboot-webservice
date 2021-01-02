package com.HrimDev.project.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @After
    public void cleanup(){
        postRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
         //given
        String title="테스트 게시글";
        String content="테스트 본문";

        postRepository.save(Posts.builder().title(title).content(content).author("ggg@gmail.com").build());

        //when
        List<Posts> postsList=postRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle(),is(title));
        assertThat(posts.getContent(),is(content));
        //오류 해결: https://m.blog.naver.com/PostView.nhn?blogId=simpolor&logNo=221327833587&proxyReferer=https:%2F%2Fwww.google.com%2F
    }
}