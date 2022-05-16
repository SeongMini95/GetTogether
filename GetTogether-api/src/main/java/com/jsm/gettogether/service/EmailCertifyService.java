package com.jsm.gettogether.service;

import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.jsm.gettogether.domain.emailcertify.enums.CertifyDiv;
import com.jsm.gettogether.domain.emailcertify.repository.EmailCertifyRepository;
import com.jsm.gettogether.domain.member.Member;
import com.jsm.gettogether.dto.emailcertify.response.ConfirmCertifyResponseDto;
import com.jsm.gettogether.exception.EmailCertifyNotFoundException;
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

    @Transactional(readOnly = true)
    public ConfirmCertifyResponseDto confirmEmailCertify(String certifyCode) throws Exception {
        EmailCertify emailCertify = emailCertifyRepository.findByCertifyCode(certifyCode).orElseThrow(EmailCertifyNotFoundException::new);

        return new ConfirmCertifyResponseDto(emailCertify);
    }

    @Transactional(readOnly = true)
    public EmailCertify findByCertifyInfo(Long id, String email, CertifyDiv certifyDiv) {
        return emailCertifyRepository.findByCertifyInfo(id, email, certifyDiv).orElseThrow(EmailCertifyNotFoundException::new);
    }
}
