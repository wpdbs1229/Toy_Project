package com.example.user.domain.dto;

import com.example.user.domain.entity.MemberEntity;
import lombok.*;

import java.util.List;


public class AuthDto {



    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class SignUp{
        private String name;
        private String password;
        private String phone;
        private List<String> roles;

        public MemberEntity toEntity(){
            return MemberEntity.builder()
                    .name(this.name)
                    .password(this.password)
                    .phone(this.phone)
                    .roles(this.roles)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class SignIn{
        private String name;
        private String password;
    }

}
