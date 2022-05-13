package com.jsm.gettogether.service;

import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.jsm.gettogether.domain.emailcertify.enums.CertifyDiv;
import com.jsm.gettogether.domain.emailcertify.repository.EmailCertifyRepository;
import com.jsm.gettogether.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmailCertifyService {

    private final EmailCertifyRepository emailCertifyRepository;

    @Transactional
    public EmailCertify insertEmailCertify(Member member, CertifyDiv certifyDiv) {
        return emailCertifyRepository.save(new EmailCertify(member, certifyDiv));
    }
}
