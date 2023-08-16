package com.day24.preProject.answer.service;


import com.day24.preProject.answer.mapper.AnswerMapper;
import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answer.repository.AnswerRepository;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.question.entity.Question;
import com.day24.preProject.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;
    public AnswerService(QuestionRepository questionRepository, AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }



    public Answer createAnswer(long question_id, long memberId, Answer answer) {
        Member member = answerMapper.mapToMember(memberId);
        Question question = answerMapper.mapToQuestion(question_id);
        answer.setQuestion(question);
        answer.setMember(member);

        return answerRepository.save(answer);
    }

    @Transactional(readOnly = true)
    public Answer findAnswer(long answer_id){
        Optional<Answer> optionalAnswer = answerRepository.findById(answer_id);
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
