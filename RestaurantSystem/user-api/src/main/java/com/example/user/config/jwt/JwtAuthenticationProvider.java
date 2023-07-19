package com.example.user.config.jwt;

import com.example.user.config.util.Ase256Util;
import com.example.user.service.MemberService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.example.user.config.common.ReadFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    private long TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24;
    private static final String KEY_ROLES = "roles";
    private final MemberService memberService;

    File file = new File("D:\\JwtSecretKey.txt");
    private final String secretKey = ReadFile.readFile(file);

    public String generateToken(String username,Long id, List<String> roles)  {
        // claims에 이름, id 저장, 권한도 저장
        Claims claims = Jwts.claims().setSubject(username).setId(id.toString());
        claims.put(KEY_ROLES, roles);

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String jwt){
        UserDetails userDetails = memberService.loadUserByUsername(this.getUserInfo(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
    public String getUserInfo(String token){
        return this.parseClaims(token).getSubject();
    }

    public boolean validateToken(String token){
        if(!StringUtils.hasText(token)) return false;

        var claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    //Token으로부터 Claims 정보를 가져오는 녀석
   public Claims parseClaims(String token){
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
   }

}
