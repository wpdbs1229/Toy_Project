package com.example.account.dto;

import lombok.*;

import java.security.SecureRandom;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfo {

    private String accountNumber;
    private Long balance;

}
