package com.jsm.gettogether.controller;

import com.jsm.gettogether.domain.member.Member;
import com.jsm.gettogether.domain.member.repository.MemberRepository;
import com.jsm.gettogether.domain.memberrole.MemberRole;
import com.jsm.gettogether.domain.memberrole.MemberRoleId;
import com.jsm.gettogether.domain.memberrole.enums.RoleDiv;
import com.jsm.gettogether.domain.memberrole.repository.MemberRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class LoginControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @AfterEach
    void tearDown() {
        memberRoleRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("로그인 성공")
    @Transactional
    void login() throws Exception {
        // given
        String password = "!test123";

        Member member = memberRepository.save(Member.builder()
                .email("test123@naver.com")
                .password(passwordEncoder.encode(password))
                .isLeave(false)
                .isDeny(false)
                .build());

        memberRoleRepository.saveAll(Arrays.asList(
                MemberRole.builder().memberRoleId(MemberRoleId.builder().member(member).roleDiv(RoleDiv.GUEST).build()).build(),
                MemberRole.builder().memberRoleId(MemberRoleId.builder().member(member).roleDiv(RoleDiv.USER).build()).build()
        ));

        String rtnUrl = "/test";

        // when
        ResultActions actions = mvc.perform(formLogin()
                .loginProcessingUrl("/login?rtnUrl=" + rtnUrl)
                .user("email", member.getEmail())
                .password(password));

        // then
        actions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(rtnUrl));
    }

    @Test
    @DisplayName("정지로 인한 로그인 실패")
    @Transactional
    void loginFail() throws Exception {
        // given
        String password = "!test123";

        Member member = memberRepository.save(Member.builder()
                .email("test123@naver.com")
                .password(passwordEncoder.encode(password))
                .isLeave(false)
                .isDeny(true)
                .build());

        // when
        ResultActions actions = mvc.perform(formLogin()
                .user("email", member.getEmail())
                .password(password));

        // then
        actions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(request().attribute("errorMessage", "이용이 정지된 회원입니다."));
    }
}