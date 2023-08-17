package com.day24.preProject.answer.service;


import com.day24.preProject.answer.mapper.AnswerMapper;
import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answer.repository.AnswerRepository;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.question.entity.Question;
import com.day24.preProject.question.repository.QuestionRepository;
import com.day24.preProject.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnswerService {
    private final QuestionService questionService;

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;

    public AnswerService(QuestionService questionService, QuestionRepository questionRepository, AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.questionService = questionService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    public Answer createAnswer(long questionId, long memberId, Answer answer) {
        Member member = answerMapper.mapToMember(memberId);
        if (questionService.findQuestion(questionId).getMember().getMemberId() == memberId) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        Question question = answerMapper.mapToQuestion(questionId);
        answer.setQuestion(question);
        answer.setMember(member);

        return answerRepository.save(answer);
    }

    @Transactional(readOnly = true)
    public Answer findAnswer(long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        return optionalAnswer.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }
    @Transactional(readOnly = true)
    public Answer findAnswerByDeleted(long answerId, boolean deleted){
        Optional<Answer> optionalAnswer = answerRepository.findByAnswerIdAndDeleted(answerId, deleted);
        return optionalAnswer.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }
//
//    @Transactional(readOnly = true)
//    public Answer findAnswer(long answerId){
//        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
//        return optionalAnswer.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
//    }

    @Transactional(readOnly = true)
    public Page<Answer> findAnswersByQuestionIdAndDeleted(long questionId, boolean deleted, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, null);
        return answerRepository.findByQuestionIdAndDeleted(questionId, deleted, pageable);
    }
      //관리자용
//    @Transactional(readOnly = true)
//    public Page<Answer> findAllAnswer(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return answerRepository.findAll(pageRequest);
//    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void updateAnswer(Answer answer, long memberId){
        Answer findAnswer = findAnswerByDeleted(answer.getAnswerId(), answer.isDeleted());
        if(findAnswer.getMember().getMemberId() != memberId) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        Optional.ofNullable(answer.getBody())
                .ifPresent(body -> findAnswer.setBody(body));

        answerRepository.save(findAnswer);
    }

    public void acceptAnswer(Answer answer, long memberId){
        Answer findAnswer = findAnswer(answer.getAnswerId());
        Question question = findAnswer.getQuestion();
        if(question.getMember().getMemberId() != memberId)
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        if(findAnswer.isAccepted()){
            findAnswer.setAccepted(false);
            if(!question.isAccepted()) {
                question.setAccepted(false);
            }
        }
        else {findAnswer.setAccepted(true);
            if (!question.isAccepted()) {
                question.setAccepted(true);
            }
        }

        answerRepository.save(findAnswer);
        questionRepository.save(question);

    }

    public void deleteAnswer(long answerId, long memberId){
        Answer answer = findAnswer(answerId);
        if(answer.getMember().getMemberId() != memberId) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        answer.setDeleted(true);
        answerRepository.save(answer);

    }







}
