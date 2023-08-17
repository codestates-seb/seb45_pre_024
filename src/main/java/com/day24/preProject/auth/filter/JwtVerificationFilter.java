package com.day24.preProject.auth.filter;

import com.day24.preProject.auth.jwt.JwtTokenizer;
import com.day24.preProject.auth.userdetails.MemberDetailsService;
import com.day24.preProject.auth.utils.AuthorityUtils;
import com.day24.preProject.member.entity.Member;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final AuthorityUtils authorityUtils;
    private final MemberDetailsService memberDetailsService;
    public JwtVerificationFilter(JwtTokenizer jwtTokenizer, AuthorityUtils authorityUtils, MemberDetailsService memberDetailsService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberDetailsService = memberDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            Map<String, Object> claims = verifyAccessJwt(request);
            setAuthenticationToContext(claims);
        } catch (ExpiredJwtException accessEx) {
            try {
                String username = verfiRefreshJwt(request);
                Member member = memberDetailsService.getMember(username);
                response.setHeader("Authorization", "Bearer "+jwtTokenizer.generateAccessToken(member));
                request.setAttribute("exception", new JwtException("Access token has expired"));
            } catch (ExpiredJwtException refreshEx) {
                request.setAttribute("exception", new JwtException("Refresh token has expired"));
            }
        }
        catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Bearer ");
    }

    private Map<String, Object> verifyAccessJwt(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        return jwtTokenizer.getClaims(jws).getBody();
    }
    private String verfiRefreshJwt(HttpServletRequest request) {
        String jws = request.getHeader("Refresh");
        return jwtTokenizer.getClaims(jws).getBody().getSubject();
    }
    private void setAuthenticationToContext(Map<String, Object> claims){
        Long member_id = Long.parseLong(claims.get("member_id").toString());
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List<String>)claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(member_id, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
