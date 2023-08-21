package com.day24.preProject.answerComment.service;

import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answer.repository.AnswerRepository;
import com.day24.preProject.answerComment.entity.AnswerComment;
import com.day24.preProject.answerComment.mapper.AnswerCommentMapper;
import com.day24.preProject.answerComment.repository.AnswerCommentRepository;
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

@Service
public class AnswerCommentService {
    private final AnswerCommentRepository answerCommentRepository;
    private final AnswerRepository answerRepository;

    private final AnswerCommentMapper answerCommentMapper;

    public AnswerCommentService(AnswerCommentRepository answerCommentRepository, AnswerRepository answerRepository, AnswerCommentMapper answerCommentMapper) {
        this.answerCommentRepository = answerCommentRepository;
        this.answerRepository = answerRepository;
        this.answerCommentMapper = answerCommentMapper;
    }

    public AnswerComment createAnswerComment(long answerId, long memberId, AnswerComment answerComment) {
        Member member = answerCommentMapper.mapToMember(memberId);
        Answer answer = answerCommentMapper.mapToAnswer(answerId);
        answerComment.setMember(member);
        answerComment.setAnswer(answer);

        return answerCommentRepository.save(answerComment);
    }

    @Transactional(readOnly = true)
    public AnswerComment findAnswerComment(long answerCommentId) {
        Optional<AnswerComment> optionalAnswerComment = answerCommentRepository.findById(answerCommentId);
        return optionalAnswerComment.orElseThrow(()-> new BusinessLogicException(ExceptionCode.ANSWER_COMMENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public AnswerComment findAnswerCommentByDeleted(long answerCommentId, boolean deleted){
        Optional<AnswerComment> optionalAnswerComment = answerCommentRepository.findByAnswerCommentIdAndDeleted(answerCommentId, deleted);
        return optionalAnswerComment.orElseThrow(()-> new BusinessLogicException(ExceptionCode.ANSWER_COMMENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<AnswerComment> findAnswerCommentsByAnswerIdAndDeleted(long answerId, boolean deleted, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return answerCommentRepository.findByAnswerIdAndDeleted(answerId, deleted, pageable);
    }

      //관리자용
//    @Transactional(readOnly = true)
//    public Page<AnswerComment> findAnswerComments(int page, int size) {
//        return answerCommentRepository.findAll(PageRequest.of(page, size,
//                Sort.by("AnswerCommentId").descending()));
//    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void updateAnswerComment(AnswerComment answerComment, long memberId) {
        AnswerComment findAnswerComment = findAnswerCommentByDeleted(answerComment.getAnswerCommentId(), answerComment.isDeleted());
        if(findAnswerComment.getMember().getMemberId() != memberId) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        Optional.ofNullable(answerComment.getBody())
                .ifPresent(body -> findAnswerComment.setBody(body));


        answerCommentRepository.save(findAnswerComment);
    }

    public void deleteAnswerComment(long answerCommentId, long memberId) {
        AnswerComment answerComment = findVerifiedAnswerComment(answerCommentId);
        if(answerComment.getMember().getMemberId() != memberId) throw new BusinessLogicException(ExceptionCode.FORBIDDEN_REQUEST);
        answerComment.setDeleted(true);
        answerCommentRepository.save(answerComment);
    }

    private AnswerComment findVerifiedAnswerComment(long answerCommentId) {
        Optional<AnswerComment> optionalAnswerComment = answerCommentRepository.findById(answerCommentId);
        AnswerComment findAnswerComment =
                optionalAnswerComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ANSWER_COMMENT_NOT_FOUND));

        return findAnswerComment;
    }
}
