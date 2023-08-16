package com.day24.preProject.AnswerReply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class PostDto {
    @NotBlank
    private String body;
}
