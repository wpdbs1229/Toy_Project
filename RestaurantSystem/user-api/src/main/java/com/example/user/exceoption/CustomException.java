package com.example.user.exceoption;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
