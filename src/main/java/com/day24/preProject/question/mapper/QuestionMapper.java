package com.day24.preProject.question.mapper;

import com.day24.preProject.answer.dto.AnswerResponseDto;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.question.dto.QuestionDetailResponseDto;
import com.day24.preProject.question.dto.QuestionPatchDto;
import com.day24.preProject.question.dto.QuestionPostDto;
import com.day24.preProject.question.dto.QuestionResponseDto;
import com.day24.preProject.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);
    QuestionResponseDto questionToQuestionResponseDto(Question question);
    @Mapping(source = "id", target = "member_id")
    Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
//    default Question questionPostDtoToQuestion(QuestionPostDto questionPostDto) {
//        Member member = new Member();
//        member.setMemberId(questionPostDto.getId());
//    };

    default List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions) {

        return questions
                .stream()
                .map(question -> QuestionResponseDto
                        .builder()
                        .question_id(question.getQuestion_id())
                        .member_id(question.getMember_id())
                        .title(question.getTitle())
                        .body(question.getBody())
                        .view_count(question.getView_count())
                        .accepted(question.isAccepted())
                        .build())
                .collect(Collectors.toList());

    }

    default QuestionDetailResponseDto questionToQuestionDetailResponseDto(Question question) {
        QuestionDetailResponseDto questionDetailResponseDto =
                QuestionDetailResponseDto.builder()
                        .question_id(question.getQuestion_id())
                        .member_id(question.getMember_id())
                        .title(question.getTitle())
                        .body(question.getBody())
                        .view_count(question.getView_count())
                        .accepted(question.isAccepted())

                        .answers(question.getAnswers()
                            .stream()
                            .map(answer -> AnswerResponseDto
                                .builder()
                                .answer_id(answer.getAnswer_id())
                                .member_id(answer.getMember_id())
                                .question_id(answer.getQuestion_id())
                                .body(answer.getBody())
                                .accepted(answer.isAccepted())
                                .build())
                        .collect(Collectors.toList()))
                        .build();
        return questionDetailResponseDto;
    }



}
