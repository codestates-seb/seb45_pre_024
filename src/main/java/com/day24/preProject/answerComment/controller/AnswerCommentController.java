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

    @PostMapping
    public ResponseEntity postAnswerReply(@Valid @RequestBody AnswerCommentPostDto answerCommentPostDto) {
        answerCommentService.createAnswerReply(answerCommentMapper.answerReplyPostDtoToAnswerReply(answerCommentPostDto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{AnswerReplyId}")
    public ResponseEntity patchAnswerReply(@PathVariable("AnswerReplyId") @Positive long answerReplyId,
                                              @Valid @RequestBody AnswerCommentPatchDto answerCommentPatchDto) {
        AnswerComment answerComment = answerCommentService.updateAnswerReply(answerReplyId, answerCommentMapper.answerReplyPatchDtoToAnswerReply(answerCommentPatchDto));

        return new ResponseEntity<>(
                answerCommentMapper.answerReplyToAnswerReplyResponseDto(answerComment),
                HttpStatus.OK);
    }

    @GetMapping("/{AnswerReplyId}")
    public ResponseEntity getAnswerReply(@PathVariable("AnswerReplyId") long answerReplyId) {
        AnswerComment answerComment = answerCommentService.findAnswerReply(answerReplyId);

        return new ResponseEntity<>(
                answerCommentMapper.answerReplyToAnswerReplyResponseDto(answerComment),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAnswerReplys(@Positive @RequestParam int page,
                                             @Positive @RequestParam int size) {
        Page<AnswerComment> pageAnswerReplys = answerCommentService.findAnswerReplys(page - 1, size);
        List<AnswerComment> answerComments = pageAnswerReplys.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(answerCommentMapper.answerReplysToAnswerReplyResponseDtos(answerComments),
                        pageAnswerReplys),
                HttpStatus.OK);
    }

    @DeleteMapping("/{AnswerReplyId}")
    public ResponseEntity deleteAnswerReply(@PathVariable("AnswerReplyId") long answerReplyId) {
        answerCommentService.deleteAnswerReply(answerReplyId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
