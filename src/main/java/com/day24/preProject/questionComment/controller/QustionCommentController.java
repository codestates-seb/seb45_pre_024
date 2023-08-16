package com.day24.preProject.questionComment.controller;

import com.day24.preProject.questionComment.dto.QustionCommentPostDto;
import com.day24.preProject.questionComment.entity.QustionComment;
import com.day24.preProject.questionComment.mapper.QustionCommentMapper;
import com.day24.preProject.questionComment.service.QustionCommentService;
import com.day24.preProject.dto.MultiResponseDto;
import com.day24.preProject.dto.SingleResponseDto;
import com.day24.preProject.utils.UriCreator;
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
@RequestMapping("/v11/QustionComments")
@Validated
public class QustionCommentController {
    private final static String QustionComment_DEFAULT_URL = "/v11/QustionComments";
    private QustionCommentService QustionCommentService;
    private QustionCommentMapper mapper;

    public QustionCommentController(QustionCommentService QustionCommentService, QustionCommentMapper QustionCommentMapper) {
        this.QustionCommentService = QustionCommentService;
        this.mapper = QustionCommentMapper;
    }

    @PostMapping
    public ResponseEntity postQustionComment(@Valid @RequestBody QustionCommentPostDto QustionCommentPostDto) {
        QustionComment QustionComment = QustionCommentService.createQustionComment(mapper.QustionCommentPostDtoToQustionComment(QustionCommentPostDto));
        URI location = UriCreator.createUri(QustionComment_DEFAULT_URL, QustionComment.getQustionCommentId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{QustionComment-id}")
    public ResponseEntity patchQustionComment(@PathVariable("QustionComment-id") @Positive long QustionCommentId,
                                      @Valid @RequestBody QustionCommentPatchDto QustionCommentPatchDto) {
        QustionCommentPatchDto.setQustionCommentId(QustionCommentId);
        QustionComment QustionComment = QustionCommentService.updateQustionComment(mapper.QustionCommentPatchDtoToQustionComment(QustionCommentPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.QustionCommentToQustionCommentResponseDto(QustionComment)),
                HttpStatus.OK);
    }

    @GetMapping("/{QustionComment-id}")
    public ResponseEntity getQustionComment(@PathVariable("QustionComment-id") long QustionCommentId) {
        QustionComment QustionComment = QustionCommentService.findQustionComment(QustionCommentId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.QustionCommentToQustionCommentResponseDto(QustionComment)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQustionComments(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<QustionComment> pageQustionComments = QustionCommentService.findQustionComments(page - 1, size);
        List<QustionComment> QustionComments = pageQustionComments.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.QustionCommentsToQustionCommentResponseDtos(QustionComments),
                        pageQustionComments),
                HttpStatus.OK);
    }

    @DeleteMapping("/{QustionComment-id}")
    public ResponseEntity deleteQustionComment(@PathVariable("QustionComment-id") long QustionCommentId) {
        QustionCommentService.deleteQustionComment(QustionCommentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

