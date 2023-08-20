package com.day24.preProject.answer.controller;

import com.day24.preProject.dto.MultiResponseDto;
import com.day24.preProject.answer.dto.AnswerPatchDto;
import com.day24.preProject.answer.dto.AnswerPostDto;
import com.day24.preProject.answer.dto.AnswerResponseDto;
import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answer.mapper.AnswerMapper;
import com.day24.preProject.answer.service.AnswerService;
import com.day24.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;

    private final AnswerMapper answerMapper;

    public AnswerController(AnswerService answerService, AnswerMapper answerMapper) {
        this.answerService = answerService;
        this.answerMapper = answerMapper;
    }
    @PostMapping("/{id}")
    public ResponseEntity postAnswer(@PathVariable("id") long questionId, @RequestBody AnswerPostDto requestBody, @AuthenticationPrincipal long memberId) {
        Answer answer = answerMapper.answerPostDtoToAnswer(requestBody);
        answerService.createAnswer(questionId, memberId, answer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity getAnswers(@PathVariable("questionId") long questionId, @RequestParam int page, @RequestParam int size) {
        Page<Answer> pageAnswers = answerService.findAnswersByQuestionIdAndDeleted(questionId, false, page-1, size);
        List<Answer> answers = pageAnswers.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(answerMapper.answersToAnswerResponseDtos(answers), pageAnswers), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchAnswer(@PathVariable("id") int id, @RequestBody AnswerPatchDto requestBody, @AuthenticationPrincipal long memberId){
        Answer answer = answerMapper.answerPatchDtoToAnswer(requestBody);
        answer.setAnswerId(id);
        answerService.updateAnswer(answer, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/accept/{id}")
    public ResponseEntity patchAcceptAnswer(@PathVariable("id") int id, @AuthenticationPrincipal long memberId){
        Answer answer = answerService.findAnswer(id);
        answerService.acceptAnswer(answer, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAnswer(@PathVariable("id") int answerId, @AuthenticationPrincipal long memberId) {
        answerService.deleteAnswer(answerId, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
