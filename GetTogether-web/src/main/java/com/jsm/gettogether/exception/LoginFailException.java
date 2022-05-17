package com.jsm.gettogether.exception;

import com.jsm.gettogether.exception.enums.LoginFailCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class LoginFailException extends AuthenticationException {

    private final LoginFailCode code;

    public LoginFailException(String msg, LoginFailCode code) {
        super(msg);
        this.code = code;
    }
}
