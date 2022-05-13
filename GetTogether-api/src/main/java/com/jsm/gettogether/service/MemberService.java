package com.jsm.gettogether.service;

import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.jsm.gettogether.domain.emailcertify.enums.CertifyDiv;
import com.jsm.gettogether.domain.member.Member;
import com.jsm.gettogether.domain.member.repository.MemberRepository;
import com.jsm.gettogether.domain.memberrole.MemberRole;
import com.jsm.gettogether.domain.memberrole.MemberRoleId;
import com.jsm.gettogether.domain.memberrole.enums.RoleDiv;
import com.jsm.gettogether.domain.memberrole.repository.MemberRoleRepository;
import com.jsm.gettogether.dto.member.request.SignUpRequestDto;
import com.jsm.gettogether.eventpublisher.event.SignUpEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
