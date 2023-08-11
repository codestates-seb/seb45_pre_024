package com.day24.preProject.auth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorityUtils {
    @Value("${mail.address.admin}")
    private String adminMail;
    private List<String> ADMIN_ROLES = List.of("ADMIN", "USER");
    private List<String> USER_ROLES = List.of("USER");

    public List<GrantedAuthority> createAuthorities(List<String> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toList());
    }
    public List<String> createRoles(String email){
        if (email.equals(adminMail)) return ADMIN_ROLES;
        return USER_ROLES;
    }
}
