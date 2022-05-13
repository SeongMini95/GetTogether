package com.jsm.gettogether.domain.emailcertify.repository;

import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.jsm.gettogether.domain.emailcertify.QEmailCertify.emailCertify;

@RequiredArgsConstructor
public class EmailCertifyRepositoryCustomImpl implements EmailCertifyRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public Optional<EmailCertify> findByCertifyCode(String certifyCode) {
        LocalDateTime now = LocalDateTime.now();

        return Optional.ofNullable(factory
                .selectFrom(emailCertify)
                .where(emailCertify.certifyCode.eq(certifyCode),
                        emailCertify.isUse.eq(false),
                        emailCertify.createDatetime.before(now),
                        emailCertify.expireDatetime.after(now))
                .orderBy(emailCertify.id.desc())
                .fetchFirst());
    }
}
