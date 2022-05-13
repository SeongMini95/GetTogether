package com.jsm.gettogether.domain.memberrole.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum RoleDiv {

    GUEST("손님", "1"),
    USER("일반 사용자", "2"),
    ADMIN("관리자", "3");

    private final String desc;
    private final String code;

    public static RoleDiv ofCode(String code) {
        return Arrays.stream(RoleDiv.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("RoleDiv, code=[%s]가 존재하지 않습니다.", code)));
    }
}
