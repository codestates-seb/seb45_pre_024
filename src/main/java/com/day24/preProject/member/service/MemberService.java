package com.day24.preProject.member.service;

import com.day24.preProject.auth.utils.AuthorityUtils;
import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepositoryrepository;
    private final AuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepositoryrepository, AuthorityUtils authorityUtils) {
        this.memberRepositoryrepository = memberRepositoryrepository;
        this.authorityUtils = authorityUtils;
    }

    public void createMember(Member member) {
        verifyMember(member);
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        memberRepositoryrepository.save(member);
    }
    @Transactional
    public Long findMember(Member member){
        Optional<Member> optionalMember = memberRepositoryrepository.findByUsername(member.getUsername());
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        if (!findMember.getMemberStatus().equals(Member.MemberStatus.MEMBER_ACTIVE)) throw new BusinessLogicException(ExceptionCode.INVALID_MEMBER_STATUS);
        if (!findMember.getPassword().equals(member.getPassword())) throw new BusinessLogicException(ExceptionCode.MEMBER_WRONG_PASSWORD);
        return findMember.getMemberId();
    }
    @Transactional(readOnly = true)
    public Member findMember(long memberId){
        Optional<Member> optionalMember = memberRepositoryrepository.findById(memberId);
        return optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member){
        return null;
    }

    public void deleteMember(long memberId){
        Member member = findMember(memberId);
        member.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
        memberRepositoryrepository.save(member);
    }
    private void verifyMember(Member member){
        Optional<Member> optionalMember = memberRepositoryrepository.findByEmail(member.getEmail());
        if (optionalMember.isPresent() && optionalMember.get().getMemberStatus() != Member.MemberStatus.MEMBER_ANAUTHORIZED) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
        optionalMember = memberRepositoryrepository.findByUsername(member.getUsername());
        if (optionalMember.isPresent() && optionalMember.get().getMemberStatus() != Member.MemberStatus.MEMBER_ANAUTHORIZED) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
}
