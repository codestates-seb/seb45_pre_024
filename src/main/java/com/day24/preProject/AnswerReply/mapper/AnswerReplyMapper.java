package com.day24.preProject.AnswerReply.mapper;

import com.day24.preProject.answer.dto.AnswerPatchDto;
import com.day24.preProject.answer.dto.AnswerPostDto;
import com.day24.preProject.answer.dto.AnswerResponseDto;
import com.day24.preProject.answer.entity.Answer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerReplyMapper {

    Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto);
    Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto);
    AnswerResponseDto answerToAnswerResponseDto(Answer answer);


}
