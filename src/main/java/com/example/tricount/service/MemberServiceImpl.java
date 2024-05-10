package com.example.tricount.service;

import com.example.tricount.dto.CreateMemberRequestDTO;
import com.example.tricount.dto.SignInRequestDTO;
import com.example.tricount.entity.Member;
import com.example.tricount.entity.Settlement;
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
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("not existed username : " + username));
    }

    @Override
    @Transactional
    public Member join(String nickname, String username, String password) {
        Member newMember = new Member(nickname, username, password);
        Member result = memberRepository.save(newMember);
        return result;
    }

    @Override
    @Transactional
    public JwtToken signIn(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken token = jwtTokenProvider.generateToken(authentication);
        log.info("로그인 성공, 유저: {}, 토큰: {}", username, token.toString());
        return token;
    }

}
