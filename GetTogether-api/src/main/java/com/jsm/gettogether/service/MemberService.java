package com.jsm.gettogether.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.jsm.gettogether.domain.emailcertify.enums.CertifyDiv;
import com.jsm.gettogether.domain.member.Member;
import com.jsm.gettogether.domain.member.repository.MemberRepository;
import com.jsm.gettogether.domain.memberrole.MemberRole;
import com.jsm.gettogether.domain.memberrole.MemberRoleId;
import com.jsm.gettogether.domain.memberrole.enums.RoleDiv;
import com.jsm.gettogether.domain.memberrole.repository.MemberRoleRepository;
import com.jsm.gettogether.dto.emailcertify.response.CertifyInfoResponseDto;
import com.jsm.gettogether.dto.member.request.SignUpRequestDto;
import com.jsm.gettogether.eventpublisher.event.SignUpEvent;
import com.jsm.gettogether.util.AESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final EmailCertifyService emailCertifyService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void signUp(SignUpRequestDto requestDto) {
        String encPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = memberRepository.save(requestDto.toEntity(encPassword));

        memberRoleRepository.save(MemberRole.builder()
                .memberRoleId(MemberRoleId.builder()
                        .member(member)
                        .roleDiv(RoleDiv.GUEST)
                        .build())
                .build());

        EmailCertify emailCertify = emailCertifyService.insertEmailCertify(member, CertifyDiv.SIGN_UP);

        publisher.publishEvent(new SignUpEvent(member, emailCertify));
    }

    @Transactional
    public void signUpCertify(String encCode) throws Exception {
        String[] splitEncInfo = encCode.split("\\.");
        String encInfo = splitEncInfo[0];
        String key = new String(Base64.getDecoder().decode(splitEncInfo[1]), StandardCharsets.UTF_8);
        String iv = new String(Base64.getDecoder().decode(splitEncInfo[2]), StandardCharsets.UTF_8);

        String jsonInfo = AESUtil.decrypt(encInfo, key, iv);
        CertifyInfoResponseDto info = new ObjectMapper().readValue(jsonInfo, CertifyInfoResponseDto.class);

        EmailCertify emailCertify = emailCertifyService.findByCertifyInfo(info.getId(), info.getEmail(), CertifyDiv.SIGN_UP);
        emailCertify.used();

        memberRoleRepository.save(MemberRole.builder()
                .memberRoleId(MemberRoleId.builder()
                        .member(emailCertify.getMember())
                        .roleDiv(RoleDiv.USER)
                        .build())
                .build());
    }
}
