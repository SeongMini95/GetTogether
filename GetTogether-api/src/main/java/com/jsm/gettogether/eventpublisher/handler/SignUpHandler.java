package com.jsm.gettogether.eventpublisher.handler;

import com.jsm.gettogether.eventpublisher.event.SignUpEvent;
import com.jsm.gettogether.util.MailHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Profile("!not-email")
public class SignUpHandler {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @TransactionalEventListener
    public void sendSignUpEmail(SignUpEvent event) throws Exception {
        Map<String, Object> context = new HashMap<>();
        context.put("certifyCode", event.getEmailCertify().getCertifyCode());

        MailHandler mailHandler = new MailHandler(mailSender);
        mailHandler.setFrom("modify879@gmail.com", "GetTogether");
        mailHandler.setTo(event.getMember().getEmail());
        mailHandler.setSubject("회원가입 인증 for GetTogether");
        mailHandler.setContentTemplate("mail/signUp", context, templateEngine);

        mailHandler.send();
    }
}
