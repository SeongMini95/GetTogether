package com.jsm.gettogether.controller;

import com.jsm.gettogether.dto.member.request.SignUpRequestDto;
import com.jsm.gettogether.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public void signUp(@RequestBody SignUpRequestDto requestDto) {
        memberService.signUp(requestDto);
    }

    @GetMapping("/signUp/certify")
    public void signUpCertify(@RequestParam String code) throws Exception {
        memberService.signUpCertify(code);
    }
}
