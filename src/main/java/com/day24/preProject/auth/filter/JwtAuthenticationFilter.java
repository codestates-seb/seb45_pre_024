package com.day24.preProject.auth.filter;

import com.day24.preProject.auth.jwt.JwtTokenizer;
import com.day24.preProject.member.entity.Member;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenizer jwtTokenizer) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("username!! :"+request.getParameter("username"));
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password"))
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        Member member = (Member) authResult.getPrincipal();
        response.setHeader("Authorization", "Bearer "+jwtTokenizer.generateAccessToken(member));
        response.setHeader("Refresh", jwtTokenizer.generateRefreshToken(member));
    }
}
