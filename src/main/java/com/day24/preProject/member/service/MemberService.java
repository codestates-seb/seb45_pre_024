package com.day24.preProject.member.service;

import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public void createMember(Member member) {
        verifyMember(member);
        repository.save(member);
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId){
        Optional<Member> optionalMember = repository.findById(memberId);
        return optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member){
        return null;
    }

    public void deleteMember(long memberId){
        Member member = findMember(memberId);
        member.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
        repository.save(member);
    }
    private void verifyMember(Member member){
        Optional<Member> optionalMember = repository.findByEmail(member.getEmail());
        if (optionalMember.isPresent() && optionalMember.get().getMemberStatus() != Member.MemberStatus.MEMBER_ANAUTHORIZED) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
        optionalMember = repository.findByUsername(member.getUsername());
        if (optionalMember.isPresent() && optionalMember.get().getMemberStatus() != Member.MemberStatus.MEMBER_ANAUTHORIZED) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
}
