package com.day24.preProject.answerReply.controller;

import com.day24.preProject.answerReply.dto.AnswerReplyPatchDto;
import com.day24.preProject.answerReply.dto.AnswerReplyPostDto;
import com.day24.preProject.answerReply.entity.AnswerReply;
import com.day24.preProject.answerReply.mapper.AnswerReplyMapper;
import com.day24.preProject.answerReply.service.AnswerReplyService;
import com.day24.preProject.dto.MultiResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/AnswerReplys")
@Validated
public class AnswerReplyController {
    private AnswerReplyService answerReplyService;
    private AnswerReplyMapper answerReplyMapper;

    public AnswerReplyController( AnswerReplyService answerReplyService, AnswerReplyMapper answerReplyMapper) {
        this.answerReplyService = answerReplyService;
        this.answerReplyMapper = answerReplyMapper;
    }

    @PostMapping
    public ResponseEntity postAnswerReply(@Valid @RequestBody AnswerReplyPostDto answerReplyPostDto) {
        answerReplyService.createAnswerReply(answerReplyMapper.answerReplyPostDtoToAnswerReply(answerReplyPostDto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{AnswerReply-id}")
    public ResponseEntity patchAnswerReply(@PathVariable("AnswerReply-id") @Positive long answerReplyId,
                                              @Valid @RequestBody AnswerReplyPatchDto answerReplyPatchDto) {
        AnswerReply answerReply = answerReplyService.updateAnswerReply(answerReplyId, answerReplyMapper.answerReplyPatchDtoToAnswerReply(answerReplyPatchDto));

        return new ResponseEntity<>(
                answerReplyMapper.answerReplyToAnswerReplyResponseDto(answerReply),
                HttpStatus.OK);
    }

    @GetMapping("/{AnswerReply-id}")
    public ResponseEntity getAnswerReply(@PathVariable("AnswerReply-id") long answerReplyId) {
        AnswerReply answerReply = answerReplyService.findAnswerReply(answerReplyId);

        return new ResponseEntity<>(
                answerReplyMapper.answerReplyToAnswerReplyResponseDto(answerReply),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAnswerReplys(@Positive @RequestParam int page,
                                             @Positive @RequestParam int size) {
        Page<AnswerReply> pageAnswerReplys = answerReplyService.findAnswerReplys(page - 1, size);
        List<AnswerReply> AnswerReplys = pageAnswerReplys.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(answerReplyMapper.answerReplysToAnswerReplyResponseDtos(AnswerReplys),
                        pageAnswerReplys),
                HttpStatus.OK);
    }

    @DeleteMapping("/{AnswerReply-id}")
    public ResponseEntity deleteAnswerReply(@PathVariable("AnswerReply-id") long answerReplyId) {
        answerReplyService.deleteAnswerReply(answerReplyId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
