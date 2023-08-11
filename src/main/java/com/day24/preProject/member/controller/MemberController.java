package com.day24.preProject.member.controller;

import com.day24.preProject.member.dto.MemberPostDto;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.member.mapper.MemberMapper;
import com.day24.preProject.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;
    private final MemberMapper mapper;

    public MemberController(MemberService service, MemberMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto requestBody){
        Member member = mapper.memberPostToMember(requestBody);
        service.createMember(member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){
        service.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}