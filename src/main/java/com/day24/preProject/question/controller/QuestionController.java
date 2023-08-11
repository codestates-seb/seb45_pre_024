package com.day24.preProject.question.controller;


import com.day24.preProject.dto.MultiResponseDto;
import com.day24.preProject.question.dto.QuestionPatchDto;
import com.day24.preProject.question.dto.QuestionPostDto;
import com.day24.preProject.question.dto.QuestionResponseDto;
import com.day24.preProject.question.entity.Question;
import com.day24.preProject.question.mapper.QuestionMapper;
import com.day24.preProject.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity postQuestion(@RequestBody QuestionPostDto requstBody) {
        Question question = questionMapper.questionPostDtoToQuestion(requstBody);
        questionService.createQuestion(question);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getQuestion(@PathVariable("id") int question_id) {
        Question question = questionService.findQuestion(question_id);
        QuestionResponseDto questionResponseDto = questionMapper.questionToQuestionResponseDto(question);
        return new ResponseEntity<>(questionResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(@RequestParam int page, @RequestParam int size) {
        Page<Question> pageQuestions = questionService.findAllQuestion(false, page-1, size);
        System.out.println("page");
        List<Question> questions = pageQuestions.getContent();
        System.out.println("list");

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        questionMapper.questionsToQuestionResponseDtos(questions), pageQuestions), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchQuestion(@PathVariable("id") int id, @RequestBody QuestionPatchDto requestBody){
        Question question = questionMapper.questionPatchDtoToQuestion(requestBody);
        questionService.updateQuestion(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuestion(@PathVariable("id") int question_id) {
        Question question = questionService.findQuestion(question_id);
        question.setDeleted(true);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
