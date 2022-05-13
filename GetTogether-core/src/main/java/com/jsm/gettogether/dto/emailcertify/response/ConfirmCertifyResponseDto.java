package com.jsm.gettogether.dto.emailcertify.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsm.gettogether.domain.emailcertify.EmailCertify;
import com.jsm.gettogether.domain.emailcertify.enums.CertifyDiv;
import com.jsm.gettogether.util.AESUtil;
import lombok.Getter;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ConfirmCertifyResponseDto {

    private boolean result;
    private String code;
    private String url;

    public ConfirmCertifyResponseDto() {
        this.result = false;
        this.code = "";
        this.url = "/certify/error";
    }

    public ConfirmCertifyResponseDto(EmailCertify emailCertify) throws Exception {
        this.result = true;
        this.code = generateCode(emailCertify);
        this.url = getUrl(emailCertify.getCertifyDiv());
    }

    private String generateCode(EmailCertify emailCertify) throws Exception {
        Map<String, Object> info = new HashMap<>();
        info.put("id", emailCertify.getId());
        info.put("email", emailCertify.getMember().getEmail());

        String jsonInfo = new ObjectMapper().writeValueAsString(info);
        String encInfo = AESUtil.encrypt(jsonInfo, emailCertify.getCertifyKey(), emailCertify.getCertifyIv());
        String encKey = Base64.getEncoder().encodeToString(emailCertify.getCertifyKey().getBytes());
        String encIv = Base64.getEncoder().encodeToString(emailCertify.getCertifyIv().getBytes());

        return encInfo + "." + encKey + "." + encIv;
    }

    private String getUrl(CertifyDiv certifyDiv) {
        switch (certifyDiv) {
            case SIGN_UP:
                return "/api/member/signUp/certify";
        }

        return "/certify/error";
    }
}
