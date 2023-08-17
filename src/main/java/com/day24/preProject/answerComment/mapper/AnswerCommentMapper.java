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
                        .answer_id(answer.getAnswer_id())
                        .body(answer.getBody())
                        .member_id(answer.getMember_id())
                        .answer_comment_id(answer.getAnswer_comment_id())
                        .modified_at(answer.getModified_at())
                        .created_at(answer.getCreated_at())
                        .build())
                .collect(Collectors.toList());
    }
}
