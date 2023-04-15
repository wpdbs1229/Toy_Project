package com.example.user.service;

import com.example.user.domain.dto.AuthDto;
import com.example.user.domain.dto.AuthDto.SignUp;
import com.example.user.domain.entity.MemberEntity;
import com.example.user.domain.repository.MemberRepository;
import com.example.user.exceoption.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import static com.example.user.exceoption.ErrorCode.*;
import static com.example.user.exceoption.ErrorCode.ALREADY_RESISTER_USER;
import static com.example.user.exceoption.ErrorCode.NOT_EXIST_USER;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username+"해당 유저를 찾을 수 없습니다."));
    }

    public MemberEntity register(SignUp member) throws CustomException {
        boolean exists = memberRepository.existsByName(member.getName());
        if (exists) {
            throw new CustomException(ALREADY_RESISTER_USER);
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member.toEntity());
    }

    public MemberEntity login(AuthDto.SignIn member) {
        var user = memberRepository.findByName(member.getName())
                .orElseThrow(() -> new CustomException(NOT_EXIST_USER));

        if (!passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new CustomException(NOT_SAME_PASSWORD);
        }

        return user;
    }


}
