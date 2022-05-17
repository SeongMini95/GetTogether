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
    private String nickname;

    @Builder
    public SignUpRequestDto(String email, String password, String passwordConfirm, String nickname) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.nickname = nickname;
    }

    public Member toEntity(String encPassword) {
        return Member.builder()
                .email(email)
                .password(encPassword)
                .nickname(nickname)
                .isLeave(false)
                .isDeny(false)
                .build();
    }
}
