package com.day24.preProject.question.dto;

import com.day24.preProject.questionComment.dto.QuestionCommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QuestionResponseDto {

    private long question_id;

    private long member_id;

    private String username;

    private String title;

    private String body;

    private List<QuestionCommentResponseDto> question_comment;

    private int view_count;

    private boolean accepted;

    private LocalDateTime created_at;

    private LocalDateTime modified_at;


}
