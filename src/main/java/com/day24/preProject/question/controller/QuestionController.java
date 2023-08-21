package com.day24.preProject.question.controller;


import com.day24.preProject.dto.MultiResponseDto;
import com.day24.preProject.question.dto.QuestionPatchDto;
import com.day24.preProject.question.dto.QuestionPostDto;
import com.day24.preProject.question.dto.QuestionResponseDto;
import com.day24.preProject.question.entity.Question;
import com.day24.preProject.question.mapper.QuestionMapper;
import com.day24.preProject.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;


    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    @PostMapping
    public ResponseEntity postQuestion(@RequestBody QuestionPostDto requstBody, @AuthenticationPrincipal long memberId) {
        Question question = questionMapper.questionPostDtoToQuestion(requstBody);
        questionService.createQuestion(question, memberId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getQuestion(@PathVariable("id") long questionId, boolean deleted) {
        Question question = questionService.getQuestionAndUpdateViewCount(questionId, deleted);

        QuestionResponseDto questionDetailResponseDto =
                questionMapper.questionToQuestionResponseDto(question);

        return new ResponseEntity<>(questionDetailResponseDto, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity getQuestionsBytext(@RequestParam int page, @RequestParam int size, @RequestParam String query) {
        Page<Question> pageQuestions = questionService.findQuestionByTextAndDeleted(query,false, page-1, size);
        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        questionMapper.questionsToQuestionResponseDtos(questions), pageQuestions), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(@RequestParam int page, @RequestParam int size) {
        Page<Question> pageQuestions = questionService.findAllQuestion(false, page-1, size);
        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        questionMapper.questionsToQuestionResponseDtos(questions), pageQuestions), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchQuestion(@PathVariable("id") int id, @RequestBody QuestionPatchDto requestBody, @AuthenticationPrincipal long memberId){
        Question question = questionMapper.questionPatchDtoToQuestion(requestBody);
        question.setQuestionId(id);
        questionService.updateQuestion(question, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuestion(@PathVariable("id") int questionId, @AuthenticationPrincipal long memberId) {
        questionService.deleteQuestion(questionId, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
