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
    @Mapping(source = "createdAt", target = "created_at")
    QuestionResponseDto questionToQuestionResponseDto(Question question);
    Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
    default Member mapToMember(long id) {
        Member member = new Member();
        member.setMember_id(id);

        return member;
    }

    default List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions) {

        return questions
                .stream()
                .map(question -> QuestionResponseDto
                        .builder()
                        .question_id(question.getQuestion_id())
                        .member_id(question.getMember().getMember_id())
                        .username(question.getMember().getUsername())
                        .title(question.getTitle())
                        .body(question.getBody())
                        .view_count(question.getView_count())
                        .accepted(question.isAccepted())
                        .created_at(question.getCreated_at())
                        .modified_at(question.getModified_at())
                        .build())
                .collect(Collectors.toList());

    }

    default QuestionDetailResponseDto questionToQuestionDetailResponseDto(Question question) {
        QuestionDetailResponseDto questionDetailResponseDto =
                QuestionDetailResponseDto.builder()
                        .question_id(question.getQuestion_id())
                        .member_id(question.getMember().getMember_id())
                        .username(question.getMember().getUsername())
                        .title(question.getTitle())
                        .body(question.getBody())
                        .view_count(question.getView_count())
                        .accepted(question.isAccepted())
                        .createdAt(question.getCreated_at())
                        .modified_at(question.getModified_at())

                        .answers(question.getAnswers()
                            .stream()
                            .map(answer -> AnswerResponseDto
                                .builder()
                                .answer_id(answer.getAnswer_id())
                                .member_id(answer.getMember().getMember_id())
                                .username(answer.getMember().getUsername())
                                .question_id(answer.getQuestion().getQuestion_id())
                                .body(answer.getBody())
                                .accepted(answer.isAccepted())
                                .created_at(answer.getCreated_at())
                                .modified_at(answer.getModified_at())
                                .build())
                        .collect(Collectors.toList()))
                        .build();
        return questionDetailResponseDto;
    }



}
