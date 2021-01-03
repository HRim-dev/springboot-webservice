package com.HrimDev.project.springboot.web;

import com.HrimDev.project.springboot.domain.posts.Posts;
import com.HrimDev.project.springboot.domain.posts.PostsRepository;
import com.HrimDev.project.springboot.web.dto.PostsSaveRequestDto;
import com.HrimDev.project.springboot.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    //MockMvc(가짜사용자 만들기)
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc= MockMvcBuilders.webAppContextSetup(context)
                            .apply(springSecurity())
                            .build();
    }

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")//가짜 사용자를 만듬(ROLE_USER권한을 가진 사용자가 API요청하는 것과 동일한 효과)
    public void Posts_등록된다() throws Exception{
        //given
        String title="title";
        String content="content";
        PostsSaveRequestDto requestDto=PostsSaveRequestDto.builder().title(title).content(content).author("author").build();

        String url="http://localhost:"+port+"/api/v1/posts";

        //when
        //ResponseEntity<Long> responseEntity=restTemplate.postForEntity(url,requestDto,Long.class);
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                             .content(new ObjectMapper().writeValueAsString(requestDto)))
                             .andExpect(status().isOk());
        //then
        //assertThat(responseEntity.getStatusCode(),is(HttpStatus.OK));
        //assertThat(responseEntity.getBody(),greaterThan(0L));
        //assertThat 오류 해결: https://www.lesstif.com/java/hamcrest-junit-test-case-18219426.html
        List<Posts> all=postsRepository.findAll();
        assertThat(all.get(0).getTitle(),is(title));
        assertThat(all.get(0).getContent(),is(content));
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정된다() throws Exception{
        //given
        Posts savePosts= postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        Long updateid= savePosts.getId();
        String expectedTitle="title2";
        String expectedContent="content2";

        PostsUpdateRequestDto requestDto=PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

        String url="http://localhost:"+port+"/api/v1/posts/"+updateid;

        HttpEntity<PostsUpdateRequestDto> requestEntity=new HttpEntity<>(requestDto);

        //when
        //ResponseEntity<Long> responseEntity=restTemplate.exchange(url, HttpMethod.PUT,requestEntity, Long.class);
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        //assertThat(responseEntity.getStatusCode(),is(HttpStatus.OK));
        //assertThat(responseEntity.getBody(),greaterThan(0L));

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle(),is(expectedTitle));
        assertThat(all.get(0).getContent(),is(expectedContent));

    }
}