package com.day24.preProject.question.dto;

import com.day24.preProject.answer.dto.AnswerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Getter
@Builder
public class QuestionDetailResponseDto {
    private long question_id;

    private long member_id;

    private String username;

    private String title;

    private String body;

    private int view_count;

    private boolean accepted;

    private LocalDateTime createdAt;

    private LocalDateTime modified_at;

    private List<AnswerResponseDto> answers;
}
