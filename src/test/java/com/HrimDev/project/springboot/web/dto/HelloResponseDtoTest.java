package com.HrimDev.project.springboot.web.dto;

import org.junit.Test;

//import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복기능테스트(){
        //given
        String name="test";
        int amount=1000;

        //when
        HelloResponseDto dto=new HelloResponseDto(name,amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);//검증하고 싶은 대상을 메소드 인자로 받음(같은 값 비교:isEqualTo(값)
        assertThat(dto.getAmount()).isEqualTo(amount);
    }

}