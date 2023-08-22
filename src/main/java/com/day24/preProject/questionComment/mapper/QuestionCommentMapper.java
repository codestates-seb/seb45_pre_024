package com.day24.preProject.questionComment.mapper;

import com.day24.preProject.question.entity.Question;
import com.day24.preProject.questionComment.dto.QuestionCommentPatchDto;
import com.day24.preProject.questionComment.dto.QuestionCommentPostDto;
import com.day24.preProject.questionComment.dto.QuestionCommentResponseDto;
import com.day24.preProject.questionComment.entity.QuestionComment;
import com.day24.preProject.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionCommentMapper {

    QuestionComment questionCommentPostDtoToQuestionComment(QuestionCommentPostDto questionQuestionCommentPostDto);
    QuestionComment questionCommentPatchDtoToQuestionComment(QuestionCommentPatchDto questionCommentPatchDto);
    QuestionCommentResponseDto questionCommentToQuestionCommentResponseDto(QuestionComment questionComment);

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

    default List<QuestionCommentResponseDto> questionCommentsToQuestionCommentResponseDtos(List<QuestionComment> questionComments){
        return questionComments.stream()
                .map(questionComment -> QuestionCommentResponseDto.builder()
                        .username(questionComment.getMember().getUsername())
                        .body(questionComment.getBody())
                        .modified_at(questionComment.getModifiedAt())
                        .created_at(questionComment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}