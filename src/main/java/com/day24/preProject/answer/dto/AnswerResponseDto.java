package com.day24.preProject.answer.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
public class AnswerResponseDto {

    private long answerId;

    private long questionId;

    private long memberId;

    private String username;

    private String body;

    private boolean accepted;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
