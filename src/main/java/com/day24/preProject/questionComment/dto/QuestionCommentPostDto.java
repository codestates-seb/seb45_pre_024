package com.day24.preProject.questionComment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class QuestionCommentPostDto {
    private Long id;
    @NotBlank
    private String body;
}
