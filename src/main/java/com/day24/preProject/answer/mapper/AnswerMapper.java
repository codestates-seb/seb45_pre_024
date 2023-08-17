package com.day24.preProject.answer.mapper;

import com.day24.preProject.answer.dto.AnswerPatchDto;
import com.day24.preProject.answer.dto.AnswerPostDto;
import com.day24.preProject.answer.dto.AnswerResponseDto;
import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answer.dto.AnswerResponseDto;
import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

//    @Mapping(source = "id", target = "member")
    Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto);
    Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto);
    AnswerResponseDto answerToAnswerResponseDto(Answer answer);

    default Member mapToMember(long id) {
        Member member = new Member();
        member.setMemberId(id);
        return member;
    }
    default Question mapToQuestion(long id) {
        Question question = new Question();
        question.setQuestionId(id);
        return question;
    }
    default List<AnswerResponseDto> answersToAnswerResponseDtos(List<Answer> answers) {

        return answers
                .stream()
                .map(answer -> AnswerResponseDto
                        .builder()
                        .answerId(answer.getAnswerId())
                        .memberId(answer.getMember().getMemberId())
                        .username(answer.getMember().getUsername())
                        .questionId(answer.getQuestion().getQuestionId())
                        .body(answer.getBody())
                        .accepted(answer.isAccepted())
                        .created_at(answer.getCreatedAt())
                        .modified_at(answer.getModifiedAt())
                        .build())
                .collect(Collectors.toList());

    }


}
