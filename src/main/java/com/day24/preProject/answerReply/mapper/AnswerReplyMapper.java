package com.day24.preProject.answerReply.mapper;

import com.day24.preProject.answerReply.dto.AnswerReplyPatchDto;
import com.day24.preProject.answerReply.dto.AnswerReplyPostDto;
import com.day24.preProject.answerReply.dto.AnswerReplyResponseDto;
import com.day24.preProject.answerReply.entity.AnswerReply;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnswerReplyMapper {

    AnswerReply answerReplyPostDtoToAnswerReply(AnswerReplyPostDto answerAnswerReplyPostDto);
    AnswerReply answerReplyPatchDtoToAnswerReply(AnswerReplyPatchDto answerPatchDto);
    AnswerReplyResponseDto answerReplyToAnswerReplyResponseDto(AnswerReply answer);

    default List<AnswerReplyResponseDto> answerReplysToAnswerReplyResponseDtos(List<AnswerReply> answers){
        return answers.stream()
                .map(answer -> AnswerReplyResponseDto.builder()
                        .answer_id(answer.getAnswer_id())
                        .body(answer.getBody())
                        .member_id(answer.getMember_id())
                        .answer_reply_id(answer.getAnswerReply_id())
                        .modified_at(answer.getModified_at())
                        .createdAt(answer.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
