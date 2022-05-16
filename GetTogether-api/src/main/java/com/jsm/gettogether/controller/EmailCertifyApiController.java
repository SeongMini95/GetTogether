package com.jsm.gettogether.controller;

import com.jsm.gettogether.dto.emailcertify.response.ConfirmCertifyResponseDto;
import com.jsm.gettogether.service.EmailCertifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/certify")
public class EmailCertifyApiController {

    private final EmailCertifyService emailCertifyService;

    @GetMapping
    public ConfirmCertifyResponseDto confirmEmailCertify(@RequestParam String code) throws Exception {
        return emailCertifyService.confirmEmailCertify(code);
    }
}
