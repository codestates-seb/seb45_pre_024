package com.day24.preProject.member.service;

import com.day24.preProject.auth.utils.AuthorityUtils;
import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, AuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    public void createMember(Member member) {
        verifyMember(member);
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        memberRepository.save(member);
    }
    @Transactional(readOnly = true)
    public boolean checkUsername(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.isPresent();
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member){
        return null;
    }

    public void deleteMember(long memberId){
        Member member = findMember(memberId);
        member.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
        memberRepository.save(member);
    }
    private void verifyMember(Member member){
        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
        if (optionalMember.isPresent() && optionalMember.get().getMemberStatus() != Member.MemberStatus.MEMBER_ANAUTHORIZED) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
        optionalMember = memberRepository.findByUsername(member.getUsername());
        if (optionalMember.isPresent() && optionalMember.get().getMemberStatus() != Member.MemberStatus.MEMBER_ANAUTHORIZED) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
}
