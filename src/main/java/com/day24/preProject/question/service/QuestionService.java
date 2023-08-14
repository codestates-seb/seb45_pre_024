package com.day24.preProject.question.service;

import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.question.entity.Question;
import com.day24.preProject.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    public QuestionService(QuestionRepository questionRepository) { this.questionRepository = questionRepository;}

    public Question createQuestion(Question question) {

        return questionRepository.save(question);
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void countView_count(Question question) {
        questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public Question findQuestion(long question_id, boolean deleted){
        Optional<Question> optionalQuestion = questionRepository.findByQuestion_idAndDeleted(question_id, deleted);
        return optionalQuestion.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Question findQuestion(long question_id){
        Optional<Question> optionalQuestion = questionRepository.findById(question_id);
        return optionalQuestion.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<Question> findAllQuestion(boolean deleted, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return questionRepository.findAllByDeleted(deleted, pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<Question> findAllQuestion(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return questionRepository.findAll(pageRequest);
    }

    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Question updateQuestion(Question question){
        Question findQuestion = findQuestion(question.getQuestion_id());

        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getBody())
                .ifPresent(body -> findQuestion.setBody(body));
        Optional.ofNullable(question.isAccepted())
                .ifPresent(accepted -> findQuestion.setAccepted(accepted));

        return questionRepository.save(findQuestion);
    }

    public void deleteQuestion(long questionId){
        Question question = findQuestion(questionId);
        questionRepository.save(question);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateView_count(Question question) {
        question.setView_count(question.getView_count()+1);
    }


}
