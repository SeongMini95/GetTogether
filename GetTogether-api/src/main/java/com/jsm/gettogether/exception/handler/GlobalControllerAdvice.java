package com.jsm.gettogether.exception.handler;

import com.jsm.gettogether.dto.emailcertify.response.ConfirmCertifyResponseDto;
import com.jsm.gettogether.exception.EmailCertifyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(EmailCertifyNotFoundException.class)
    public ResponseEntity<ConfirmCertifyResponseDto> emailCertifyError() {
        return new ResponseEntity<>(new ConfirmCertifyResponseDto(), HttpStatus.OK);
    }
}
