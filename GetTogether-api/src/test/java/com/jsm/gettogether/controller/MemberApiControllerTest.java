package com.jsm.gettogether.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.jsm.gettogether.domain.emailcertify.repository.EmailCertifyRepository;
import com.jsm.gettogether.domain.member.Member;
import com.jsm.gettogether.domain.member.repository.MemberRepository;
import com.jsm.gettogether.domain.memberrole.MemberRole;
import com.jsm.gettogether.domain.memberrole.enums.RoleDiv;
import com.jsm.gettogether.domain.memberrole.repository.MemberRoleRepository;
import com.jsm.gettogether.dto.member.request.SignUpRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("not-email")
class MemberApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private EmailCertifyRepository emailCertifyRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @AfterEach
    void tearDown() {
        emailCertifyRepository.deleteAllInBatch();
        memberRoleRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp() throws Exception {
        // given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("test123@naver.com")
                .password("!test123")
                .passwordConfirm("!test123")
                .build();

        // when
        ResultActions actions = mvc.perform(post("/api/member/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpRequestDto)));

        Member member = memberRepository.findAll().get(0);
        MemberRole memberRole = memberRoleRepository.findAll().get(0);
        EmailCertify emailCertify = emailCertifyRepository.findAll().get(0);

        // then
        actions
                .andExpect(status().isOk());

        assertThat(member.getEmail()).isEqualTo(signUpRequestDto.getEmail());
        assertThat(passwordEncoder.matches(signUpRequestDto.getPassword(), member.getPassword())).isTrue();
        assertThat(memberRole.getMemberRoleId().getRoleDiv()).isEqualTo(RoleDiv.GUEST);
        assertThat(emailCertify).isNotNull();
    }
}