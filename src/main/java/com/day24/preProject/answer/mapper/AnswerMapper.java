package com.day24.preProject.answer.mapper;

import com.day24.preProject.answer.dto.AnswerPatchDto;
import com.day24.preProject.answer.dto.AnswerPostDto;
import com.day24.preProject.answer.dto.AnswerResponseDto;
import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answer.dto.AnswerResponseDto;
import com.day24.preProject.answer.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mapping(source = "id", target = "member_id")
    Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto);
    Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto);
    AnswerResponseDto answerToAnswerResponseDto(Answer answer);

    default List<AnswerResponseDto> answersToAnswerResponseDtos(List<Answer> answers) {

        return answers
                .stream()
                .map(answer -> AnswerResponseDto
                        .builder()
                        .answer_id(answer.getAnswer_id())
                        .member_id(answer.getMember_id())
                        .question_id(answer.getQuestion_id())
                        .body(answer.getBody())
                        .accepted(answer.isAccepted())
                        .build())
                .collect(Collectors.toList());

    }


}
