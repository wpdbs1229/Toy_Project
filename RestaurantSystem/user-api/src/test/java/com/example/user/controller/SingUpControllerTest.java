package com.example.user.controller;

import com.example.user.domain.dto.AuthDto;
import com.example.user.domain.entity.MemberEntity;
import com.example.user.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SingUpControllerTest {

    @Autowired
    MemberService memberService;
    @Test
    void register(){
        //given
        List<String> role = new ArrayList<>();
        role.add("User");
        AuthDto.SignUp signUp = AuthDto.SignUp.builder()
                .name("이제윤")
                .phone("01010101")
                .password("123123")
                .roles(role)
                .build();
        //when
        MemberEntity member = memberService.register(signUp);

        //then

        assertEquals("이제윤",member.getUsername());
     }
}