package com.day24.preProject.questionComment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v11/QustionComments")
@Validated
public class QuestionCommentController {
//    private QuestionCommentService questionCommentService;
//    private QuestionCommentMapper questionCommentMapper;
//
//    public QuestionCommentController(QuestionCommentService questionCommentService, QuestionCommentMapper questionCommentMapper) {
//        this.questionCommentService = questionCommentService;
//        this.questionCommentMapper = questionCommentMapper;
//    }
//
//    @PostMapping
//    public ResponseEntity postQustionComment(@Valid @RequestBody QustionCommentPostDto QustionCommentPostDto) {
//        QustionComment QustionComment = questionCommentService.createQustionComment(mapper.QustionCommentPostDtoToQustionComment(QustionCommentPostDto));
//        URI location = UriCreator.createUri(QustionComment_DEFAULT_URL, QustionComment.getQustionCommentId());
//
//        return ResponseEntity.created(location).build();
//    }
//
//    @PatchMapping("/{QustionComment-id}")
//    public ResponseEntity patchQustionComment(@PathVariable("QustionComment-id") @Positive long QustionCommentId,
//                                      @Valid @RequestBody QustionCommentPatchDto QustionCommentPatchDto) {
//        QustionCommentPatchDto.setQustionCommentId(QustionCommentId);
//        QustionComment QustionComment = QustionCommentService.updateQustionComment(mapper.QustionCommentPatchDtoToQustionComment(QustionCommentPatchDto));
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(mapper.QustionCommentToQustionCommentResponseDto(QustionComment)),
//                HttpStatus.OK);
//    }
//
//    @GetMapping("/{QustionComment-id}")
//    public ResponseEntity getQustionComment(@PathVariable("QustionComment-id") long QustionCommentId) {
//        QustionComment QustionComment = QustionCommentService.findQustionComment(QustionCommentId);
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(mapper.QustionCommentToQustionCommentResponseDto(QustionComment)),
//                HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity getQustionComments(@Positive @RequestParam int page,
//                                     @Positive @RequestParam int size) {
//        Page<QustionComment> pageQustionComments = QustionCommentService.findQustionComments(page - 1, size);
//        List<QustionComment> QustionComments = pageQustionComments.getContent();
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(mapper.QustionCommentsToQustionCommentResponseDtos(QustionComments),
//                        pageQustionComments),
//                HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{QustionComment-id}")
//    public ResponseEntity deleteQustionComment(@PathVariable("QustionComment-id") long QustionCommentId) {
//        QustionCommentService.deleteQustionComment(QustionCommentId);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}

