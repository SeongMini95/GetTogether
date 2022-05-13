package com.jsm.gettogether.domain.emailcertify;

import com.jsm.gettogether.domain.BaseTimeEntity;
import com.jsm.gettogether.domain.emailcertify.converter.CertifyDivConverter;
import com.jsm.gettogether.domain.emailcertify.enums.CertifyDiv;
import com.jsm.gettogether.domain.member.Member;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Entity
@Table(name = "email_certify")
public class EmailCertify extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Convert(converter = CertifyDivConverter.class)
    @Column(name = "certify_div", nullable = false)
    private CertifyDiv certifyDiv;

    @Column(name = "certify_code", nullable = false)
    private String certifyCode;

    @Column(name = "certify_key", nullable = false, length = 32)
    private String certifyKey;

    @Column(name = "certify_iv", nullable = false, length = 16)
    private String certifyIv;

    @Column(name = "is_use", nullable = false)
    private boolean isUse;

    @Column(name = "expire_datetime", nullable = false)
    private LocalDateTime expireDatetime;

    @Builder
    public EmailCertify(Member member, CertifyDiv certifyDiv, String certifyCode, String certifyKey, String certifyIv, boolean isUse, LocalDateTime expireDatetime) {
        this.member = member;
        this.certifyDiv = certifyDiv;
        this.certifyCode = certifyCode;
        this.certifyKey = certifyKey;
        this.certifyIv = certifyIv;
        this.isUse = isUse;
        this.expireDatetime = expireDatetime;
    }

    public EmailCertify(Member member, CertifyDiv certifyDiv) {
        this.member = member;
        this.certifyDiv = certifyDiv;
        this.certifyCode = RandomStringUtils.randomAlphanumeric(255);
        this.certifyKey = RandomStringUtils.randomAlphanumeric(32);
        this.certifyIv = RandomStringUtils.randomAlphanumeric(16);
        this.isUse = false;
        this.expireDatetime = LocalDateTime.now().plusDays(1L);
    }
}
