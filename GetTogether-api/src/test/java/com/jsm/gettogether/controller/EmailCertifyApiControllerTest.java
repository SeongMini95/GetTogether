package com.jsm.gettogether.controller;

import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.jsm.gettogether.domain.emailcertify.enums.CertifyDiv;
import com.jsm.gettogether.domain.emailcertify.repository.EmailCertifyRepository;
import com.jsm.gettogether.domain.member.Member;
import com.jsm.gettogether.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class EmailCertifyApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailCertifyRepository emailCertifyRepository;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @AfterEach
    void tearDown() {
        emailCertifyRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원가입 이메일 인증 성공")
    void confirmEmailCertify() throws Exception {
        // given
        Member member = memberRepository.save(Member.builder()
                .email("test123@naver.com")
                .password("!test123")
                .nickname("test123")
                .isLeave(false)
                .isDeny(false)
                .build());

        EmailCertify emailCertify = emailCertifyRepository.save(new EmailCertify(member, CertifyDiv.SIGN_UP));

        // when
        ResultActions actions = mvc.perform(get("/api/certify?code=" + emailCertify.getCertifyCode()));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.url").value("/api/member/signUp/certify"));
    }
}