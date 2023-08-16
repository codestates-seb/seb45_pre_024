package com.day24.preProject.AnswerReply.service;

import com.day24.preProject.AnswerReply.repository.AnswerReplyRepository;
import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@org.springframework.stereotype.Service
public class AnswerReplyService {
    private final AnswerReplyRepository AnswerReplyRepository;

    public AnswerReplyService(AnswerReplyRepository AnswerReplyRepository) {
        this.AnswerReplyRepository = AnswerReplyRepository;
    }

    public AnswerReply createAnswerReply(AnswerReply AnswerReply) {

        String AnswerReplyCode = AnswerReply.getAnswerReplyCode().toUpperCase();


        verifyExistAnswerReply(AnswerReplyCode);
        AnswerReply.setAnswerReplyCode(AnswerReplyCode);

        return AnswerReplyRepository.save(AnswerReply);
    }

    public AnswerReply updateAnswerReply(AnswerReply AnswerReply) {

        AnswerReply findAnswerReply = findVerifiedAnswerReply(AnswerReply.getAnswerReplyId());

        Optional.ofNullable(AnswerReply.getKorName())
                .ifPresent(korName -> findAnswerReply.setKorName(korName));
        Optional.ofNullable(AnswerReply.getEngName())
                .ifPresent(engName -> findAnswerReply.setEngName(engName));
        Optional.ofNullable(AnswerReply.getPrice())
                .ifPresent(price -> findAnswerReply.setPrice(price));

        Optional.ofNullable(AnswerReply.getAnswerReplyStatus())
                .ifPresent(AnswerReplyStatus -> findAnswerReply.setAnswerReplyStatus(AnswerReplyStatus));

        return AnswerReplyRepository.save(findAnswerReply);
    }

    public AnswerReply findAnswerReply(long AnswerReplyId) {
        return findVerifiedAnswerReplyByQuery(AnswerReplyId);
    }

    public Page<AnswerReply> findAnswerReplys(int page, int size) {
        return AnswerReplyRepository.findAll(PageRequest.of(page, size,
                Sort.by("AnswerReplyId").descending()));
    }

    public void deleteAnswerReply(long AnswerReplyId) {
        AnswerReply AnswerReply = findVerifiedAnswerReply(AnswerReplyId);
        AnswerReplyRepository.delete(AnswerReply);
    }

    public AnswerReply findVerifiedAnswerReply(long AnswerReplyId) {
        Optional<AnswerReply> optionalAnswerReply = AnswerReplyRepository.findById(AnswerReplyId);
        AnswerReply findAnswerReply =
                optionalAnswerReply.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.AnswerReply_NOT_FOUND));

        return findAnswerReply;
    }

    private void verifyExistAnswerReply(String AnswerReplyCode) {
        Optional<AnswerReply> AnswerReply = AnswerReplyRepository.findByAnswerReplyCode(AnswerReplyCode);
        if(AnswerReply.isPresent())
            throw new BusinessLogicException(ExceptionCode.AnswerReply_CODE_EXISTS);
    }

    private AnswerReply findVerifiedAnswerReplyByQuery(long AnswerReplyId) {
        Optional<AnswerReply> optionalAnswerReply = AnswerReplyRepository.findByAnswerReply(AnswerReplyId);
        AnswerReply findAnswerReply =
                optionalAnswerReply.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.AnswerReply_NOT_FOUND));

        return findAnswerReply;
    }
}
