package com.day24.preProject.question.controller;


import com.day24.preProject.answer.dto.AnswerResponseDto;
import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answer.mapper.AnswerMapper;
import com.day24.preProject.answer.service.AnswerService;
import com.day24.preProject.dto.MultiResponseDto;
import com.day24.preProject.question.dto.QuestionDetailResponseDto;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity postQuestion(@RequestBody QuestionPostDto requstBody, @AuthenticationPrincipal long member_id) {
        Question question = questionMapper.questionPostDtoToQuestion(requstBody);
        questionService.createQuestion(question, member_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getQuestion(@PathVariable("id") long question_id, boolean deleted) {
        Question question = questionService.findQuestionByDeleted(question_id, deleted);
        question.setView_count(question.getView_count()+1);
        questionService.countView_count(question);

        QuestionDetailResponseDto questionDetailResponseDto =
                questionMapper.questionToQuestionDetailResponseDto(question);

        return new ResponseEntity<>(questionDetailResponseDto, HttpStatus.OK);
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
    public ResponseEntity patchQuestion(@PathVariable("id") int id, @RequestBody QuestionPatchDto requestBody, @AuthenticationPrincipal long member_id){
        Question question = questionMapper.questionPatchDtoToQuestion(requestBody);
        question.setQuestion_id(id);
        questionService.updateQuestion(question, member_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuestion(@PathVariable("id") int question_id, @AuthenticationPrincipal long member_id) {
        questionService.deleteQuestion(question_id, member_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
