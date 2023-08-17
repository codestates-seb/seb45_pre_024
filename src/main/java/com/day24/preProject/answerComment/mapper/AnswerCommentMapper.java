package com.day24.preProject.answerComment.mapper;

import com.day24.preProject.answerComment.dto.AnswerCommentPatchDto;
import com.day24.preProject.answerComment.dto.AnswerCommentPostDto;
import com.day24.preProject.answerComment.dto.AnswerCommentResponseDto;
import com.day24.preProject.answerComment.entity.AnswerComment;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnswerCommentMapper {

    AnswerComment answerReplyPostDtoToAnswerReply(AnswerCommentPostDto answerAnswerCommentPostDto);
    AnswerComment answerReplyPatchDtoToAnswerReply(AnswerCommentPatchDto answerPatchDto);
    AnswerCommentResponseDto answerReplyToAnswerReplyResponseDto(AnswerComment answer);

    default List<AnswerCommentResponseDto> answerReplysToAnswerReplyResponseDtos(List<AnswerComment> answers){
        return answers.stream()
                .map(answer -> AnswerCommentResponseDto.builder()
                        .answerId(answer.getAnswerId())
                        .body(answer.getBody())
                        .memberId(answer.getMemberId())
                        .answer_commentId(answer.getAnswerCommentId())
                        .modifiedAt(answer.getModifiedAt())
                        .createdAt(answer.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
