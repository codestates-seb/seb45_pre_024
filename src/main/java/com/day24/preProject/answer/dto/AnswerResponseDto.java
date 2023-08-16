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

    private long answer_id;

    private long question_id;

    private long member_id;

    private String username;

    private String body;

    private boolean accepted;

    private LocalDateTime created_at;

    private LocalDateTime modified_at;
}
