package com.day24.preProject.answerComment.controller;

import com.day24.preProject.answerComment.dto.AnswerCommentPatchDto;
import com.day24.preProject.answerComment.dto.AnswerCommentPostDto;
import com.day24.preProject.answerComment.entity.AnswerComment;
import com.day24.preProject.answerComment.mapper.AnswerCommentMapper;
import com.day24.preProject.answerComment.service.AnswerCommentService;
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
@RequestMapping("/answer/comment")
@Validated
public class AnswerCommentController {
    private AnswerCommentService answerCommentService;
    private AnswerCommentMapper answerCommentMapper;

    public AnswerCommentController(AnswerCommentService answerCommentService, AnswerCommentMapper answerCommentMapper) {
        this.answerCommentService = answerCommentService;
        this.answerCommentMapper = answerCommentMapper;
    }

    @PostMapping("/{answerId}")
    public ResponseEntity postAnswerComment(@PathVariable("answerId") long answerId, @Valid @RequestBody AnswerCommentPostDto answerCommentPostDto, @AuthenticationPrincipal long memberId) {
        AnswerComment answerComment = answerCommentMapper.answerCommentPostDtoToAnswerComment(answerCommentPostDto);
        answerCommentService.createAnswerComment(answerId, memberId, answerComment);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity getAnswerComment(@PathVariable("id") long answerCommentId) {
//        AnswerComment answerComment = answerCommentService.findAnswerComment(answerCommentId);
//
//        return new ResponseEntity<>(
//                answerCommentMapper.answerCommentToAnswerCommentResponseDto(answerComment),
//                HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity getAnswerComments(@Positive @RequestParam int page,
//                                             @Positive @RequestParam int size) {
//        Page<AnswerComment> pageAnswerComments = answerCommentService.findAnswerComments(page - 1, size);
//        List<AnswerComment> answerComments = pageAnswerComments.getContent();
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(answerCommentMapper.answerCommentsToAnswerCommentResponseDtos(answerComments),
//                        pageAnswerComments),
//                HttpStatus.OK);
//    }

    @PatchMapping("/{id}")
    public ResponseEntity patchAnswerComment(@PathVariable("id") @Positive long id,
                                             @Valid @RequestBody AnswerCommentPatchDto requestbody, @AuthenticationPrincipal long memberId) {
        AnswerComment answerComment = answerCommentMapper.answerCommentPatchDtoToAnswerComment(requestbody);
        answerComment.setAnswerCommentId(id);
        answerCommentService.updateAnswerComment(answerComment, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAnswerComment(@PathVariable("id") long answerCommentId, @AuthenticationPrincipal long memberId) {
        answerCommentService.deleteAnswerComment(answerCommentId, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
