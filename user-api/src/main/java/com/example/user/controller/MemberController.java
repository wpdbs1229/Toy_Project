package com.example.user.controller;



import com.example.user.config.jwt.JwtAuthenticationProvider;
import com.example.user.domain.dto.AuthDto.SignIn;
import com.example.user.domain.dto.AuthDto.SignUp;
import com.example.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUp request){
      var result = ResponseEntity.ok(memberService.register(request));
      return result;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignIn request) {
        var member = memberService.login(request);
        var token = jwtAuthenticationProvider.generateToken(member.getName(), member.getId(), member.getRoles());
        return ResponseEntity.ok(token);
    }
}
