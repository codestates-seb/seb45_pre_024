package com.day24.preProject.answerComment.mapper;

import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answerComment.dto.AnswerCommentPatchDto;
import com.day24.preProject.answerComment.dto.AnswerCommentPostDto;
import com.day24.preProject.answerComment.dto.AnswerCommentResponseDto;
import com.day24.preProject.answerComment.entity.AnswerComment;
import com.day24.preProject.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnswerCommentMapper {

    AnswerComment answerCommentPostDtoToAnswerComment(AnswerCommentPostDto answerAnswerCommentPostDto);
    AnswerComment answerCommentPatchDtoToAnswerComment(AnswerCommentPatchDto answerPatchDto);
    AnswerCommentResponseDto answerCommentToAnswerCommentResponseDto(AnswerComment answerComment);

    default Member mapToMember(long id) {
        Member member = new Member();
        member.setMemberId(id);
        return member;
    }
    default Answer mapToAnswer(long id) {
        Answer answer = new Answer();
        answer.setAnswerId(id);
        return answer;
    }

    default List<AnswerCommentResponseDto> answerCommentsToAnswerCommentResponseDtos(List<AnswerComment> answerComments){
        return answerComments.stream()
                .map(answerComment -> AnswerCommentResponseDto.builder()
                        .username(answerComment.getMember().getUsername())
                        .body(answerComment.getBody())
                        .modified_at(answerComment.getModifiedAt())
                        .created_at(answerComment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
