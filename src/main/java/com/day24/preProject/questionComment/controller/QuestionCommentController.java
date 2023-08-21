package com.day24.preProject.questionComment.controller;

import com.day24.preProject.questionComment.dto.QuestionCommentPatchDto;
import com.day24.preProject.questionComment.dto.QuestionCommentPostDto;
import com.day24.preProject.questionComment.entity.QuestionComment;
import com.day24.preProject.questionComment.mapper.QuestionCommentMapper;
import com.day24.preProject.questionComment.service.QuestionCommentService;
import com.day24.preProject.dto.MultiResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Controller
@RequestMapping("/question/comment")
@Validated
public class QuestionCommentController {
    private QuestionCommentService questionCommentService;
    private QuestionCommentMapper questionCommentMapper;

    public QuestionCommentController(QuestionCommentService questionCommentService, QuestionCommentMapper questionCommentMapper) {
        this.questionCommentService = questionCommentService;
        this.questionCommentMapper = questionCommentMapper;
    }

    @PostMapping("/{questionId}")
    public ResponseEntity postQuestionComment(@PathVariable("questionId") long questionId, @Valid @RequestBody QuestionCommentPostDto questionCommentPostDto, @AuthenticationPrincipal long memberId) {
        QuestionComment questionComment = questionCommentMapper.questionCommentPostDtoToQuestionComment(questionCommentPostDto);
        questionCommentService.createQuestionComment(questionId, memberId, questionComment);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity getQuestionComment(@PathVariable("id") long questionCommentId) {
//        QuestionComment questionComment = questionCommentService.findQuestionComment(questionCommentId);
//
//        return new ResponseEntity<>(
//                questionCommentMapper.questionCommentToQuestionCommentResponseDto(questionComment),
//                HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity getQuestionComments(@Positive @RequestParam int page,
//                                             @Positive @RequestParam int size) {
//        Page<QuestionComment> pageQuestionComments = questionCommentService.findQuestionComments(page - 1, size);
//        List<QuestionComment> questionComments = pageQuestionComments.getContent();
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(questionCommentMapper.questionCommentsToQuestionCommentResponseDtos(questionComments),
//                        pageQuestionComments),
//                HttpStatus.OK);
//    }

    @PatchMapping("/{id}")
    public ResponseEntity patchQuestionComment(@PathVariable("id") @Positive long id,
                                             @Valid @RequestBody QuestionCommentPatchDto requestbody, @AuthenticationPrincipal long memberId) {
        QuestionComment questionComment = questionCommentMapper.questionCommentPatchDtoToQuestionComment(requestbody);
        questionComment.setQuestionCommentId(id);
        questionCommentService.updateQuestionComment(questionComment, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuestionComment(@PathVariable("id") long questionCommentId, @AuthenticationPrincipal long memberId) {
        questionCommentService.deleteQuestionComment(questionCommentId, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

