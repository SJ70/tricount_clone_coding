package com.example.tricount.controller;

import com.example.tricount.dto.CreateMemberRequestDTO;
import com.example.tricount.dto.SignInRequestDTO;
import com.example.tricount.entity.Member;
import com.example.tricount.jwt.JwtToken;
import com.example.tricount.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Member> join(@RequestBody CreateMemberRequestDTO requestDTO) {
        try {
            Member member = memberService.join(requestDTO.name(), requestDTO.id(), requestDTO.password());
            return ResponseEntity.status(HttpStatus.CREATED).body(member);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtToken> signIn(@RequestBody SignInRequestDTO requestDTO) {
        String username = requestDTO.userId();
        String password = requestDTO.password();
        JwtToken token = memberService.signIn(username, password);
        return ResponseEntity.ok(token);
    }

}
