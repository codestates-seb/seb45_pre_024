package com.day24.preProject.auth.userdetails;

import com.day24.preProject.auth.utils.AuthorityUtils;
import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.member.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final AuthorityUtils authorityUtils;

    public MemberDetailsService(MemberRepository memberRepository, AuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return new MemberDetails(findMember);
    }

    private final class MemberDetails extends Member implements UserDetails {
        MemberDetails(Member member) {
            setMemberId(member.getMemberId());
            setUsername(member.getUsername());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setRoles(member.getRoles());
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
