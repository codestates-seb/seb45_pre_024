package com.day24.preProject.questionComment.service;

import com.day24.preProject.question.entity.Question;
import com.day24.preProject.question.repository.QuestionRepository;
import com.day24.preProject.questionComment.entity.QuestionComment;
import com.day24.preProject.questionComment.mapper.QuestionCommentMapper;
import com.day24.preProject.questionComment.repository.QuestionCommentRepository;
import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import com.day24.preProject.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class QuestionCommentService {
    private final QuestionCommentRepository questionCommentRepository;
    private final QuestionRepository questionRepository;

    private final QuestionCommentMapper questionCommentMapper;

    public QuestionCommentService(QuestionCommentRepository questionCommentRepository, QuestionRepository questionRepository, QuestionCommentMapper questionCommentMapper) {
        this.questionCommentRepository = questionCommentRepository;
        this.questionRepository = questionRepository;
        this.questionCommentMapper = questionCommentMapper;
    }

    public QuestionComment createQuestionComment(long questionId, long memberId, QuestionComment questionComment) {
        Member member = questionCommentMapper.mapToMember(memberId);
        Question question = questionCommentMapper.mapToQuestion(questionId);
        questionComment.setMember(member);
        questionComment.setQuestion(question);

        return questionCommentRepository.save(questionComment);
    }

    @Transactional(readOnly = true)
    public QuestionComment findQuestionComment(long questionCommentId) {
        Optional<QuestionComment> optionalQuestionComment = questionCommentRepository.findById(questionCommentId);
        return optionalQuestionComment.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_COMMENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public QuestionComment findQuestionCommentByDeleted(long questionCommentId, boolean deleted){
        Optional<QuestionComment> optionalQuestionComment = questionCommentRepository.findByQuestionCommentIdAndDeleted(questionCommentId, deleted);
        return optionalQuestionComment.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_COMMENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<QuestionComment> findQuestionCommentsByQuestionIdAndDeleted(long questionId, boolean deleted, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return questionCommentRepository.findByQuestionIdAndDeleted(questionId, deleted, pageable);
    }

    //관리자용
//    @Transactional(readOnly = true)
//    public Page<QuestionComment> findQuestionComments(int page, int size) {
//        return questionCommentRepository.findAll(PageRequest.of(page, size,
//                Sort.by("QuestionCommentId").descending()));
//    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void updateQuestionComment(QuestionComment questionComment, long memberId) {
        QuestionComment findQuestionComment = findQuestionCommentByDeleted(questionComment.getQuestionCommentId(), questionComment.isDeleted());
        if(findQuestionComment.getMember().getMemberId() != memberId) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        Optional.ofNullable(questionComment.getBody())
                .ifPresent(body -> findQuestionComment.setBody(body));


        questionCommentRepository.save(findQuestionComment);
    }

    public void deleteQuestionComment(long questionCommentId, long memberId) {
        QuestionComment questionComment = findVerifiedQuestionComment(questionCommentId);
        if(questionComment.getMember().getMemberId() != memberId) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        questionComment.setDeleted(true);
        questionCommentRepository.save(questionComment);
    }

    private QuestionComment findVerifiedQuestionComment(long questionCommentId) {
        Optional<QuestionComment> optionalQuestionComment = questionCommentRepository.findById(questionCommentId);
        QuestionComment findQuestionComment =
                optionalQuestionComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.QUESTION_COMMENT_NOT_FOUND));

        return findQuestionComment;
    }
}
