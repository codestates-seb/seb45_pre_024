package com.day24.preProject.answerReply.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AnswerReplyResponseDto {

    private long answer_id;

    private long member_id;

    private long answer_reply_id;

    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime modified_at;
}
