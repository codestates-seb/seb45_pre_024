package com.day24.preProject.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class MemberCheckDto {
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;
}
