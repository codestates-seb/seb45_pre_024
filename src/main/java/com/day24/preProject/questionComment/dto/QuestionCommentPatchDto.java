package com.day24.preProject.questionComment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionCommentPatchDto {
    private Long id;
    private String body;
}
