package com.jsm.gettogether.domain.emailcertify.repository;

import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.jsm.gettogether.domain.emailcertify.enums.CertifyDiv;
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

    @Override
    public Optional<EmailCertify> findByCertifyInfo(Long id, String email, CertifyDiv certifyDiv) {
        return Optional.ofNullable(factory
                .selectFrom(emailCertify)
                .where(emailCertify.id.eq(id),
                        emailCertify.member.email.eq(email),
                        emailCertify.certifyDiv.eq(certifyDiv),
                        emailCertify.isUse.eq(false))
                .fetchOne());
    }
}
