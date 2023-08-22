package com.day24.preProject.answerComment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AnswerCommentResponseDto {
    private long answer_comment_id;

    private String username;

    private String body;

    private LocalDateTime created_at;

    private LocalDateTime modified_at;
}
