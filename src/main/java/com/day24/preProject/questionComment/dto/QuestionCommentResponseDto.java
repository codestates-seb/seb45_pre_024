package com.day24.preProject.questionComment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class QuestionCommentResponseDto {

    private long questionId;

    private long memberId;

    private long questionCommentId;

    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}