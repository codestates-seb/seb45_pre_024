package com.day24.preProject.question.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class QuestionResponseDto {

    private long questionId;

    private long memberId;

    private String username;

    private String title;

    private String body;

    private int view_count;

    private boolean accepted;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


}
