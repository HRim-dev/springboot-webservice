package com.HrimDev.project.springboot.web;

import com.HrimDev.project.springboot.domain.posts.Posts;
import com.HrimDev.project.springboot.domain.posts.PostsRepository;
import com.HrimDev.project.springboot.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception{
        //given
        String title="title";
        String content="content";
        PostsSaveRequestDto requestDto=PostsSaveRequestDto.builder().title(title).content(content).author("author").build();

        String url="http://localhost:"+port+"/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity=restTemplate.postForEntity(url,requestDto,Long.class);

        //then
        assertThat(responseEntity.getStatusCode(),is(HttpStatus.OK));
        assertThat(responseEntity.getBody(),greaterThan(0L));
        //assertThat 오류 해결: https://www.lesstif.com/java/hamcrest-junit-test-case-18219426.html

        List<Posts> all=postsRepository.findAll();
        assertThat(all.get(0).getTitle(),is(title));
        assertThat(all.get(0).getContent(),is(content));
    }
}