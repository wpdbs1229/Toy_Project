package com.example.user.exceoption;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ALREADY_RESISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "유저가 존재하지 않습니다." ),
    NOT_SAME_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    ALREADY_RESISTER_STORE(HttpStatus.BAD_REQUEST,"이미 등록된 식당입니다."),
    NOT_EXIST_STORE(HttpStatus.BAD_REQUEST,"식당이 존재하지 않습니다." );


    private final HttpStatus httpStatus;
    private final String description;
}
