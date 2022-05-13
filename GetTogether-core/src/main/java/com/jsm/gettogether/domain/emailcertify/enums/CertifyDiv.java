package com.jsm.gettogether.domain.emailcertify.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CertifyDiv {

    SIGN_UP("회원가입", "1");

    private final String desc;
    private final String code;

    public static CertifyDiv ofCode(String code) {
        return Arrays.stream(CertifyDiv.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("CertifyDiv, code=[%s]가 존재하지 않습니다.", code)));
    }
}
