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
    public ResponseEntity postAnswer(@PathVariable("id") long question_id, @RequestBody AnswerPostDto requestBody, @AuthenticationPrincipal long member_id) {
        Answer answer = answerMapper.answerPostDtoToAnswer(requestBody);
        answerService.createAnswer(question_id, member_id, answer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{/question_id}")
    public ResponseEntity getAnswers(@PathVariable("question_id") long question_id, @RequestParam int page, @RequestParam int size) {
        Page<Answer> pageAnswers = answerService.findAnswersByQuestion_idAndDeleted(question_id, false, page-1, size);
        List<Answer> answers = pageAnswers.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(answerMapper.answersToAnswerResponseDtos(answers), pageAnswers), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchAnswer(@PathVariable("id") int id, @RequestBody AnswerPatchDto requestBody, @AuthenticationPrincipal long member_id){
        Answer answer = answerMapper.answerPatchDtoToAnswer(requestBody);
        answer.setAnswer_id(id);
        answerService.updateAnswer(answer, member_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAnswer(@PathVariable("id") int answer_id, @AuthenticationPrincipal long member_id) {
        answerService.deleteAnswer(answer_id, member_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
