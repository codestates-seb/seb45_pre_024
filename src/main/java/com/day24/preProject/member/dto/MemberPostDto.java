package com.day24.preProject.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class MemberPostDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[@$!%*?&])(?=.*\\d)[A-Za-z@$!%*?&\\d]{8,20}$",
            message = "비밀번호는 8글자 이상 20글자 이하이고, 하나 이상의 영어 문자, 숫자, 특수문자가 들어가야 합니다.")
    private String password;
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;
}
