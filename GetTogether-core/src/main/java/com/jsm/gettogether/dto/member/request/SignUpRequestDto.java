package com.jsm.gettogether.dto.member.request;

import com.jsm.gettogether.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignUpRequestDto {

    private String email;
    private String password;
    private String passwordConfirm;

    @Builder
    public SignUpRequestDto(String email, String password, String passwordConfirm) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public Member toEntity(String encPassword) {
        return Member.builder()
                .email(email)
                .password(encPassword)
                .isLeave(false)
                .isDeny(false)
                .build();
    }
}
