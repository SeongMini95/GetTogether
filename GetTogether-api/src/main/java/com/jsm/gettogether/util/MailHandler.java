package com.jsm.gettogether.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MailHandler {

    private final JavaMailSender sender;
    private final MimeMessage message;
    private final MimeMessageHelper helper;

    public MailHandler(JavaMailSender sender) throws MessagingException {
        this.sender = sender;
        message = sender.createMimeMessage();
        helper = new MimeMessageHelper(message, true, "UTF-8");
    }

    public void setFrom(String fromAddress) throws MessagingException {
        helper.setFrom(fromAddress);
    }

    public void setFrom(String fromAddress, String name) throws MessagingException, UnsupportedEncodingException {
        helper.setFrom(fromAddress, name);
    }

    public void setTo(String toAddress) throws MessagingException {
        helper.setTo(toAddress);
    }

    public void setTo(List<String> toAddress) throws MessagingException {
        helper.setTo(toAddress.toArray(new String[toAddress.size()]));
    }

    public void setSubject(String subject) throws MessagingException {
        helper.setSubject(subject);
    }

    public void setContent(String content) throws MessagingException {
        helper.setText(content, true);
    }

    public void setContentTemplate(String templateName, Map<String, Object> map, SpringTemplateEngine engine) throws MessagingException {
        Context context = new Context();
        context.setVariables(map);

        helper.setText(engine.process(templateName, context), true);
    }

    public void setAttach(List<MultipartFile> files) throws MessagingException {
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename() == null ? UUID.randomUUID().toString() : file.getOriginalFilename();
            helper.addAttachment(filename, file);
        }
    }

    public void send() {
        sender.send(message);
    }
}
