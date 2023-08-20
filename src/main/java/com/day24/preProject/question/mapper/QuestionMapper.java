package com.day24.preProject.question.mapper;

import com.day24.preProject.member.entity.Member;
import com.day24.preProject.question.dto.QuestionPatchDto;
import com.day24.preProject.question.dto.QuestionPostDto;
import com.day24.preProject.question.dto.QuestionResponseDto;
import com.day24.preProject.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);
    default QuestionResponseDto questionToQuestionResponseDto(Question question){
        return QuestionResponseDto
                .builder()
                .question_id(question.getQuestionId())
                .member_id(question.getMember().getMemberId())
                .username(question.getMember().getUsername())
                .title(question.getTitle())
                .body(question.getBody())
                .view_count(question.getView_count())
                .accepted(question.isAccepted())
                .created_at(question.getCreatedAt())
                .modified_at(question.getModifiedAt())
                .build();
    };
    Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
    default Member mapToMember(long id) {
        Member member = new Member();
        member.setMemberId(id);

        return member;
    }

    default List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions) {

        return questions
                .stream()
                .map(question -> QuestionResponseDto
                        .builder()
                        .question_id(question.getQuestionId())
                        .member_id(question.getMember().getMemberId())
                        .username(question.getMember().getUsername())
                        .title(question.getTitle())
                        .body(question.getBody())
                        .view_count(question.getView_count())
                        .accepted(question.isAccepted())
                        .created_at(question.getCreatedAt())
                        .modified_at(question.getModifiedAt())
                        .build())
                .collect(Collectors.toList());

    }
}
