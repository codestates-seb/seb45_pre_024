package com.day24.preProject.answerComment.service;

import com.day24.preProject.answer.repository.AnswerRepository;
import com.day24.preProject.answerComment.entity.AnswerComment;
import com.day24.preProject.answerComment.repository.AnswerCommentRepository;
import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerCommentService {
    private final AnswerCommentRepository answerCommentRepository;
    private final AnswerRepository answerRepository;

    public AnswerCommentService(AnswerCommentRepository answerCommentRepository, AnswerRepository answerRepository) {
        this.answerCommentRepository = answerCommentRepository;
        this.answerRepository = answerRepository;
    }

    public AnswerComment createAnswerReply(AnswerComment asnwerReply) {

        return answerCommentRepository.save(asnwerReply);
    }

    public AnswerComment updateAnswerReply(long answerId, AnswerComment answerComment) {

        AnswerComment findAnswerComment = findVerifiedAnswerReply(answerId);

        Optional.ofNullable(answerComment.getBody())
                .ifPresent(findAnswerComment::setBody);


        return answerCommentRepository.save(findAnswerComment);
    }

    public AnswerComment findAnswerReply(long AnswerReplyId) {
        return findVerifiedAnswerReply(AnswerReplyId);
    }

    public Page<AnswerComment> findAnswerReplys(int page, int size) {
        return answerCommentRepository.findAll(PageRequest.of(page, size,
                Sort.by("AnswerReplyId").descending()));
    }

    public void deleteAnswerReply(long answerReplyId) {
        AnswerComment answerComment = findVerifiedAnswerReply(answerReplyId);
        answerComment.setDeleted(true);
        answerCommentRepository.save(answerComment);
    }

    private AnswerComment findVerifiedAnswerReply(long answerReplyId) {
        Optional<AnswerComment> optionalAnswerReply = answerCommentRepository.findById(answerReplyId);
        AnswerComment findAnswerComment =
                optionalAnswerReply.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ANSWER_REPLY_NOT_FOUND));

        return findAnswerComment;
    }
}
