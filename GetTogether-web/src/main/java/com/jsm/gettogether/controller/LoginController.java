package com.jsm.gettogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "member/login";
    }
}
