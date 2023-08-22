package com.day24.preProject.questionComment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class QuestionCommentResponseDto {

    private long question_id;

    private long member_id;

    private long question_comment_id;

    private String body;

    private LocalDateTime created_at;

    private LocalDateTime modified_at;
}