package com.day24.preProject.member.mapper;

import com.day24.preProject.member.dto.MemberPostDto;
import com.day24.preProject.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostToMember(MemberPostDto requestBody);
}
