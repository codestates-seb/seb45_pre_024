package com.day24.preProject.answerComment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class AnswerCommentPostDto {
    @NotBlank
    private String body;
}
