package com.day24.preProject.answer.dto;


import java.time.LocalDateTime;

public class AnswerResponseDto {

    private long answer_id;

    private long member_id;

    private long question_id;

    private String body;

    private boolean accepted;

    private LocalDateTime createdAt;

    private LocalDateTime modified_at;
}
