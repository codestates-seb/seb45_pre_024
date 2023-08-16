package com.day24.preProject.AnswerReply.controller;

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
    private final static String AnswerReply_DEFAULT_URL = "/v11/AnswerReplys";
    private AnswerReplyService answerReplyService;
    private AnswerReplyMapper mapper;

    public AnswerReplyController( AnswerReplyService answerReplyService, AnswerReplyMapper mapper) {
        this.answerReplyService = answerReplyService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postAnswerReply(@Valid @RequestBody AnswerReplyPostDto AnswerReplyPostDto) {
        AnswerReply AnswerReply = AnswerReplyService.createAnswerReply(mapper.AnswerReplyPostDtoToAnswerReply(AnswerReplyPostDto));
        URI location = UriCreator.createUri(AnswerReply_DEFAULT_URL, AnswerReply.getAnswerReplyId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{AnswerReply-id}")
    public ResponseEntity patchAnswerReply(@PathVariable("AnswerReply-id") @Positive long AnswerReplyId,
                                              @Valid @RequestBody AnswerReplyPatchDto AnswerReplyPatchDto) {
        AnswerReplyPatchDto.setAnswerReplyId(AnswerReplyId);
        AnswerReply AnswerReply = AnswerReplyService.updateAnswerReply(mapper.AnswerReplyPatchDtoToAnswerReply(AnswerReplyPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.AnswerReplyToAnswerReplyResponseDto(AnswerReply)),
                HttpStatus.OK);
    }

    @GetMapping("/{AnswerReply-id}")
    public ResponseEntity getAnswerReply(@PathVariable("AnswerReply-id") long AnswerReplyId) {
        AnswerReply AnswerReply = AnswerReplyService.findAnswerReply(AnswerReplyId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.AnswerReplyToAnswerReplyResponseDto(AnswerReply)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAnswerReplys(@Positive @RequestParam int page,
                                             @Positive @RequestParam int size) {
        Page<AnswerReply> pageAnswerReplys = AnswerReplyService.findAnswerReplys(page - 1, size);
        List<AnswerReply> AnswerReplys = pageAnswerReplys.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.AnswerReplysToAnswerReplyResponseDtos(AnswerReplys),
                        pageAnswerReplys),
                HttpStatus.OK);
    }

    @DeleteMapping("/{AnswerReply-id}")
    public ResponseEntity deleteAnswerReply(@PathVariable("AnswerReply-id") long AnswerReplyId) {
        AnswerReplyService.deleteAnswerReply(AnswerReplyId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
