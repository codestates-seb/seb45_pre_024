package com.day24.preProject.answer.service;


import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answer.repository.AnswerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    public AnswerService(AnswerRepository answerRepository) { this.answerRepository = answerRepository;}

    public Answer createAnswer(Answer answer) {

        return answerRepository.save(answer);
    }


    @Transactional(readOnly = true)
    public Answer findAnswer(long answer_id){
        Optional<Answer> optionalAnswer = answerRepository.findByAnswer_id(answer_id);
        return optionalAnswer.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }
    @Transactional(readOnly = true)
    public Answer findAnswerBydeleted(long answer_id, boolean deleted){
        Optional<Answer> optionalAnswer = answerRepository.findByAnswer_idAndDeleted(answer_id, deleted);
        return optionalAnswer.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }
//
//    @Transactional(readOnly = true)
//    public Answer findAnswer(long answer_id){
//        Optional<Answer> optionalAnswer = answerRepository.findById(answer_id);
//        return optionalAnswer.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
//    }

    @Transactional(readOnly = true)
    public List<Answer> findAnswersByQuestion_idAndDeleted(long question_id, boolean deleted) {

        return answerRepository.findByQuestion_idAndDeleted(question_id, deleted);
    }
      //관리자용
//    @Transactional(readOnly = true)
//    public Page<Answer> findAllAnswer(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return answerRepository.findAll(pageRequest);
//    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Answer updateAnswer(Answer answer){
        Answer findAnswer = findAnswerBydeleted(answer.getAnswer_id(), answer.isDeleted());

        Optional.ofNullable(answer.getBody())
                .ifPresent(body -> findAnswer.setBody(body));
        Optional.ofNullable(answer.isAccepted())
                .ifPresent(accepted -> findAnswer.setAccepted(accepted));

        return answerRepository.save(findAnswer);
    }

    public void deleteAnswer(long answer_id){
        Answer answer = findAnswer(answer_id);
        answer.setDeleted(true);
        answerRepository.save(answer);

    }







}
