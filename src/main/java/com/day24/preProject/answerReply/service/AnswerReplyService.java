package com.day24.preProject.answerReply.service;

import com.day24.preProject.answer.repository.AnswerRepository;
import com.day24.preProject.answerReply.entity.AnswerReply;
import com.day24.preProject.answerReply.repository.AnswerReplyRepository;
import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@org.springframework.stereotype.Service
public class AnswerReplyService {
    private final AnswerReplyRepository answerReplyRepository;
    private final AnswerRepository answerRepository;

    public AnswerReplyService(AnswerReplyRepository answerReplyRepository, AnswerRepository answerRepository) {
        this.answerReplyRepository = answerReplyRepository;
        this.answerRepository = answerRepository;
    }

    public AnswerReply createAnswerReply(AnswerReply asnwerReply) {

        return answerReplyRepository.save(asnwerReply);
    }

    public AnswerReply updateAnswerReply(long answerId, AnswerReply answerReply) {

        AnswerReply findAnswerReply = findVerifiedAnswerReply(answerId);

        Optional.ofNullable(answerReply.getBody())
                .ifPresent(findAnswerReply::setBody);


        return answerReplyRepository.save(findAnswerReply);
    }

    public AnswerReply findAnswerReply(long AnswerReplyId) {
        return findVerifiedAnswerReply(AnswerReplyId);
    }

    public Page<AnswerReply> findAnswerReplys(int page, int size) {
        return answerReplyRepository.findAll(PageRequest.of(page, size,
                Sort.by("AnswerReplyId").descending()));
    }

    public void deleteAnswerReply(long answerReplyId) {
        AnswerReply answerReply = findVerifiedAnswerReply(answerReplyId);
        answerReply.setDeleted(true);
        answerReplyRepository.save(answerReply);
    }

    private AnswerReply findVerifiedAnswerReply(long answerReplyId) {
        Optional<AnswerReply> optionalAnswerReply = answerReplyRepository.findById(answerReplyId);
        AnswerReply findAnswerReply =
                optionalAnswerReply.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ANSWER_REPLY_NOT_FOUND));

        return findAnswerReply;
    }
}
