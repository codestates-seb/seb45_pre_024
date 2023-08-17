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

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final QuestionService questionService;
    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;

    public AnswerService(QuestionService questionService, AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.questionService = questionService;
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    public Answer createAnswer(long question_id, long memberId, Answer answer) {
        Member member = answerMapper.mapToMember(memberId);
        if (questionService.findQuestion(question_id).getMember().getMember_id() == memberId) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
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
    public Page<Answer> findAnswersByQuestion_idAndDeleted(long question_id, boolean deleted, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_at");
        Pageable pageable = PageRequest.of(page, size, sort);
        return answerRepository.findByQuestion_idAndDeleted(question_id, deleted, pageable);
    }
      //관리자용
//    @Transactional(readOnly = true)
//    public Page<Answer> findAllAnswer(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return answerRepository.findAll(pageRequest);
//    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Answer updateAnswer(Answer answer, long member_id){
        Answer findAnswer = findAnswerBydeleted(answer.getAnswer_id(), answer.isDeleted());
        if(findAnswer.getMember().getMember_id() != member_id) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        Optional.ofNullable(answer.getBody())
                .ifPresent(body -> findAnswer.setBody(body));
        Optional.ofNullable(answer.isAccepted())
                .ifPresent(accepted -> findAnswer.setAccepted(accepted));

        return answerRepository.save(findAnswer);
    }

    public void deleteAnswer(long answer_id, long member_id){
        Answer answer = findAnswer(answer_id);
        if(answer.getMember().getMember_id() != member_id) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        answer.setDeleted(true);
        answerRepository.save(answer);

    }







}
