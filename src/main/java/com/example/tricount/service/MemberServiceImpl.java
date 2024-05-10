package com.example.tricount.service;

import com.example.tricount.dto.CreateMemberRequestDTO;
import com.example.tricount.dto.SignInRequestDTO;
import com.example.tricount.entity.Member;
import com.example.tricount.jwt.JwtToken;
import com.example.tricount.jwt.JwtTokenProvider;
import com.example.tricount.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public Member join(CreateMemberRequestDTO requestDTO) {
        Member member = new Member(
                requestDTO.name(),
                requestDTO.id(),
                requestDTO.password()
        );
        memberRepository.save(member);
        log.info("회원가입 성공 = {}", member);
        return member;
    }

    @Override
    @Transactional
    public JwtToken signIn(String userId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken token = jwtTokenProvider.generateToken(authentication);
        log.info("로그인 성공, 토큰 = {}", token.toString());
        return token;
    }

}
