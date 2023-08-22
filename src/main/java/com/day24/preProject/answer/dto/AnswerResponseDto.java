package com.day24.preProject.answer.dto;


import com.day24.preProject.answerComment.dto.AnswerCommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class AnswerResponseDto {

    private long answer_id;

    private long question_id;

    private long member_id;

    private String username;

    private String body;

    private List<AnswerCommentResponseDto> answer_comment;

    private boolean accepted;

    private LocalDateTime created_at;

    private LocalDateTime modified_at;
}
