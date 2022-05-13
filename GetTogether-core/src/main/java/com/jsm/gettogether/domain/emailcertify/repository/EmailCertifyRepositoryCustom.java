package com.jsm.gettogether.domain.emailcertify.repository;

import com.jsm.gettogether.domain.emailcertify.EmailCertify;

import java.util.Optional;

public interface EmailCertifyRepositoryCustom {

    Optional<EmailCertify> findByCertifyCode(String certifyCode);
}
