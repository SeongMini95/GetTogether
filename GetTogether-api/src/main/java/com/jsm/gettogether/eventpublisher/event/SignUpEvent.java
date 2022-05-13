package com.jsm.gettogether.eventpublisher.event;

import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.jsm.gettogether.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpEvent {

    private Member member;
    private EmailCertify emailCertify;
}
