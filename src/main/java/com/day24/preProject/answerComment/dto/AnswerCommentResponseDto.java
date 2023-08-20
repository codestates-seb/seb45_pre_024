package com.day24.preProject.answerComment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AnswerCommentResponseDto {

    private long answerId;

    private long memberId;

    private long answer_commentId;

    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
