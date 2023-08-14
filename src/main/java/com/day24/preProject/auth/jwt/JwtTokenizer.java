package com.day24.preProject.auth.jwt;

import com.day24.preProject.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Component
public class JwtTokenizer {
    @Value("${jwt.key}")
    private String secretKey;
    @Value("${jwt.accessTokenExp}")
    private int accessTokenExp;
    @Value("${jwt.refreshTokenExp}")
    private int refreshTokenExp;

    public String generateAccessToken(Member member){
        Map<String, Object> claims = new HashMap<>();
        claims.put("member_id", member.getMemberId());
        claims.put("roles", member.getRoles());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(member.getUsername())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(getTokenExp(accessTokenExp))
                .signWith(getKey())
                .compact();
    }
    public String generateRefreshToken(Member member){
        return Jwts.builder()
                .setSubject(member.getUsername())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(getTokenExp(refreshTokenExp))
                .signWith(getKey())
                .compact();
    }
    public Jws<Claims> getClaims(String jws) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(jws);
    }

    private Key getKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Date getTokenExp(int exp){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, exp);
        Date expiration = calendar.getTime();
        return expiration;
    }
}
