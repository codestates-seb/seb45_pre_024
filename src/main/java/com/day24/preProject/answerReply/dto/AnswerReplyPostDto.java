package com.day24.preProject.answerReply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class AnswerReplyPostDto {
    @NotBlank
    private String body;
}
