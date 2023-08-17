package com.day24.preProject.question.service;

import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.question.entity.Question;
import com.day24.preProject.question.mapper.QuestionMapper;
import com.day24.preProject.question.repository.QuestionRepository;
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
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    public QuestionService(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public Question createQuestion(Question question, long id) {
        Member member = questionMapper.mapToMember(id);
        question.setMember(member);


        return questionRepository.save(question);
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void countView_count(Question question) {
        questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public Question findQuestionByDeleted(long questionId, boolean deleted){
        Optional<Question> optionalQuestion = questionRepository.findByQuestionIdAndDeleted(questionId, deleted);
        if(deleted) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND);
        }
        return optionalQuestion.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }
    //관리자용
    @Transactional(readOnly = true)
    public Question findQuestion(long questionId){
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        return optionalQuestion.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<Question> findAllQuestion(boolean deleted, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt"); //최신순 정렬
        Pageable pageable = PageRequest.of(page, size, sort);
        return questionRepository.findAllByDeleted(deleted, pageable);
    }

    //관리자 용
    @Transactional(readOnly = true)
    public Page<Question> findAllQuestion(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return questionRepository.findAll(pageRequest);
    }

    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Question updateQuestion(Question question, long memberId){
        Question modifiedQuestion = findQuestion(question.getQuestionId());
        if (modifiedQuestion.getMember().getMemberId() != memberId) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);

        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> modifiedQuestion.setTitle(title));
        Optional.ofNullable(question.getBody())
                .ifPresent(body -> modifiedQuestion.setBody(body));
        Optional.ofNullable(question.isAccepted())
                .ifPresent(accepted -> modifiedQuestion.setAccepted(accepted));

        return questionRepository.save(modifiedQuestion);
    }

    public void deleteQuestion(long questionId, long memberId){
        Question question = findQuestion(questionId);
        if (question.getMember().getMemberId() != memberId) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        question.setDeleted(true);
        questionRepository.save(question);

    }

}
