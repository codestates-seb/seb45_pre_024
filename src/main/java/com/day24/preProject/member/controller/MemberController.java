package com.day24.preProject.member.controller;

import com.day24.preProject.member.dto.MemberLoginDtoTemp;
import com.day24.preProject.member.dto.MemberPostDto;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.member.mapper.MemberMapper;
import com.day24.preProject.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
@Validated
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }
    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto requestBody){
        Member member = memberMapper.memberPostToMember(requestBody);
        memberService.createMember(member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity signIn(@Valid @RequestBody MemberLoginDtoTemp requestBody){
        Member member = memberMapper.memberLoginToMember(requestBody);
        Long memberId = memberService.findMember(member);
        Map<String, Long> temp = new HashMap<>();
        temp.put("id", memberId);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
