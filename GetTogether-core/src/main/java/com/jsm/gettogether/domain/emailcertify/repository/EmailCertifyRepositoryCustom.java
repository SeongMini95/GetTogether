package com.jsm.gettogether.domain.emailcertify.repository;

import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.jsm.gettogether.domain.emailcertify.enums.CertifyDiv;

import java.util.Optional;

public interface EmailCertifyRepositoryCustom {

    Optional<EmailCertify> findByCertifyCode(String certifyCode);

    Optional<EmailCertify> findByCertifyInfo(Long id, String email, CertifyDiv certifyDiv);
}
